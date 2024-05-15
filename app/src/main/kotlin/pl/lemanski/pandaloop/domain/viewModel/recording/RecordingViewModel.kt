package pl.lemanski.pandaloop.domain.viewModel.recording

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import pl.lemanski.pandaloop.core.Recording
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.core.getBufferSizeInBytesWithTempo
import pl.lemanski.pandaloop.core.getTimeWithTempo
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.platform.PermissionManager
import pl.lemanski.pandaloop.domain.platform.i18n.Localization

class RecordingViewModel(
    private val permissionManager: PermissionManager,
    private val navigationController: NavigationController,
    private val key: Destination.RecordingScreen,
    private val localization: Localization
) : RecordingContract.ViewModel, ViewModel() {
    private var recordingLock = Mutex()

    enum class Countdown(val count: Int) {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4)
    }

    private val _stateFlow = MutableStateFlow(
        RecordingContract.State(
            countdownScrim = RecordingContract.State.CountdownScrim(
                visible = false,
                countdown = Countdown.FOUR.count,
                currentCount = 0,
                isRecording = false
            )
        )
    )

    override fun initialize() {
        val permissionState = permissionManager.checkPermissionState(PermissionManager.Permission.RECORD_AUDIO)
        if (permissionState != PermissionManager.PermissionState.GRANTED) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    val permission = permissionManager.askPermission(PermissionManager.Permission.RECORD_AUDIO)
                    if (permission != PermissionManager.PermissionState.GRANTED) {
                        navigationController.goBack()
                    } else {
                        record()
                    }
                }
            }
        } else {
            record()
        }
    }

    override val stateFlow: StateFlow<RecordingContract.State> = _stateFlow.asStateFlow()

    override fun record(): Job = viewModelScope.launch {
        if (recordingLock.isLocked) return@launch

        recordingLock.withLock {
            val countdown = key.timeSignature.toCountdown()
            val measures = key.measures
            val recordingTime = key.timeSignature.getTimeWithTempo(key.tempo) * measures
            val newRecording = Recording(key.timeSignature.getBufferSizeInBytesWithTempo(key.tempo) * measures)

            var currentCountdown = countdown

            while (currentCountdown > 0) {
                _stateFlow.update { state ->
                    state.copy(
                        countdownScrim = state.countdownScrim.copy(
                            visible = true,
                            countdown = countdown,
                            currentCount = currentCountdown,
                            isRecording = false
                        )
                    )
                }
                delay(recordingTime.toLong() / (countdown * measures))
                currentCountdown--
            }

            val recordingJob = viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    newRecording.start()
                    delay(recordingTime.toLong() + 200L) // Add some padding
                    newRecording.stop()
                }
            }

            var currentMeasure = 0
            viewModelScope.launch {
                currentCountdown = 1
                while (currentCountdown <= countdown) {
                    _stateFlow.update { state ->
                        state.copy(
                            countdownScrim = state.countdownScrim.copy(
                                visible = true,
                                currentCount = countdown,
                                countdown = currentCountdown,
                                isRecording = true
                            )
                        )
                    }
                    delay(recordingTime.toLong() / (countdown * measures))
                    currentCountdown++

                    if (currentCountdown == countdown && currentMeasure < key.measures) {
                        _stateFlow.update { state ->
                            state.copy(
                                countdownScrim = state.countdownScrim.copy(
                                    visible = true,
                                    currentCount = countdown,
                                    countdown = currentCountdown,
                                    isRecording = true
                                )
                            )
                        }
                        delay(recordingTime.toLong() / (countdown * measures))
                        currentMeasure++
                        currentCountdown = 1
                    }
                }
            }

            recordingJob.join()
            val buffer = newRecording.recordedBuffer
            val looperKey = navigationController.keyOfType<Destination.LoopScreen>() ?: return@launch
            looperKey.tracks[key.trackNumber] = buffer

            newRecording.close()
            navigationController.goBack()
        }
    }

    private fun TimeSignature.toCountdown(): Int = when (this) {
        TimeSignature.COMMON      -> Countdown.FOUR.count
        TimeSignature.THREE_FOURS -> Countdown.THREE.count
    }
}