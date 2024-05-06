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
import pl.lemanski.pandaloop.Recording
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.platform.PermissionManager
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.getTimeWithTempo

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
            recordButton = Component.IconButton(
                icon = IconResource.REC_DOT,
                onClick = ::onRecordClick
            ),
            countdownSelect = Component.TextSelect(
                label = localization.countdown,
                options = Countdown.entries.map {
                    Component.TextSelect.Option(
                        label = it.visualName(it.count),
                        value = it.count
                    )
                },
                onSelectedChanged = ::onCountdownChanged,
                selected = Countdown.FOUR.count
            ),
            countdownScrim = RecordingContract.State.CountdownScrim(
                visible = false,
                countdown = Countdown.FOUR.count,
                currentCount = 0,
                isRecording = false
            )
        )
    )

    // TODO call initialize in router
    override fun initialize() {
        val permissionState = permissionManager.checkPermissionState(PermissionManager.Permission.RECORD_AUDIO)
        if (permissionState != PermissionManager.PermissionState.GRANTED) {
            navigationController.goBack()
        }
    }

    override val stateFlow: StateFlow<RecordingContract.State> = _stateFlow.asStateFlow()

    override fun onRecordClick(): Job = viewModelScope.launch {
        if (recordingLock.isLocked) return@launch

        recordingLock.withLock {
            val countdown = Countdown.entries.find { it.count == _stateFlow.value.countdownSelect.selected }?.count ?: return@launch
            val recordingTime = key.timeSignature.getTimeWithTempo(key.tempo)
            val newRecording = Recording(recordingTime.toInt())

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
                delay(recordingTime / countdown)
                currentCountdown--
            }

            val recordingJob = viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    newRecording.start()
                    delay(recordingTime + 200L) // Add some padding
                    newRecording.stop()
                }
            }

            viewModelScope.launch {
                delay(200) // FIXME
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
                    delay(recordingTime / countdown)
                    currentCountdown++
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

    override fun onCountdownChanged(count: Int) {
        _stateFlow.update { state ->
            state.copy(
                countdownSelect = _stateFlow.value.countdownSelect.copy(selected = count),
                countdownScrim = state.countdownScrim.copy(
                    visible = false,
                    isRecording = false,
                    countdown = count
                )
            )
        }
    }

    private fun Countdown.visualName(quantity: Int): String = localization.measures(quantity)
}