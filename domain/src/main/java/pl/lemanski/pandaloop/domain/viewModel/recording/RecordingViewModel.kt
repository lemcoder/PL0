package pl.lemanski.pandaloop.domain.viewModel.recording

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import android.media.AudioRecordingConfiguration
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
import kotlinx.coroutines.withContext
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.model.timeSignature.TimeSignature
import pl.lemanski.pandaloop.domain.model.timeSignature.getTime
import pl.lemanski.pandaloop.domain.platform.permission.PermissionManager
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.back

class RecordingViewModel(
    private val permissionManager: PermissionManager,
    private val navigationService: NavigationService,
    private val key: Destination.RecordingScreen,
    context: Context
) : RecordingContract.ViewModel, ViewModel() {
    private val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
    private val countdown = key.loopContext.timeSignature.getCountdown()
    private val beatsPeriod = key.loopContext.timeSignature.getTime(key.loopContext.tempo) / countdown
    private var recordingJob: Job? = null

    private val _stateFlow = MutableStateFlow(
        RecordingContract.State(
            countdown = null,
            metronome = null
        )
    )

    init {
        val permissionState = permissionManager.checkPermissionState(PermissionManager.Permission.RECORD_AUDIO)
        val isPermissionGranted = permissionState == PermissionManager.PermissionState.GRANTED

        if (isPermissionGranted) {
            recordingJob = record()
        } else {
            askRecordingPermission()
        }
    }

    override val stateFlow: StateFlow<RecordingContract.State> = _stateFlow.asStateFlow()

    override fun record(): Job = viewModelScope.launch {
        _stateFlow.update { state ->
            state.copy(
                countdown = "$countdown"
            )
        }

        for (i in countdown downTo 1) {
            _stateFlow.update { state ->
                state.copy(
                    countdown = "$i"
                )
            }
            delay(beatsPeriod)
        }

        audioManager.registerAudioRecordingCallback(micCallback, null)

        launch {
            withContext(Dispatchers.IO) {
                val recordedTrack = key.loopContext.record()

                _stateFlow.update { state ->
                    state.copy(
                        countdown = null,
                        metronome = null
                    )
                }

                key.loopContext.setTrack(key.trackNumber, recordedTrack)
            }
        }.join()

        navigationService.back()
    }

    private fun askRecordingPermission() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val permission = permissionManager.askPermission(PermissionManager.Permission.RECORD_AUDIO)
                val isPermissionGranted = permission == PermissionManager.PermissionState.GRANTED

                if (isPermissionGranted) {
                    record()
                } else {
                    navigationService.back()
                }
            }
        }
    }

    private fun TimeSignature.getCountdown(): Int = when (this) {
        TimeSignature.COMMON      -> 4
        TimeSignature.THREE_FOURS -> 3
    }

    private val micCallback = object : AudioManager.AudioRecordingCallback() {
        override fun onRecordingConfigChanged(configs: List<AudioRecordingConfiguration>) {
            if (configs.isNotEmpty()) {
                _stateFlow.update { state ->
                    state.copy(
                        countdown = null,
                        metronome = RecordingContract.State.Metronome(
                            beatsPeriod
                        )
                    )
                }
            }
        }
    }

    override fun onCleared() {
        audioManager.unregisterAudioRecordingCallback(micCallback)
        recordingJob?.cancel()
    }
}