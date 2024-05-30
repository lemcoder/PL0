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
import kotlinx.coroutines.withContext
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.core.getTimeWithTempo
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.platform.permission.PermissionManager
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.back

class RecordingViewModel(
    private val permissionManager: PermissionManager,
    private val navigationService: NavigationService,
    private val key: Destination.RecordingScreen,
) : RecordingContract.ViewModel, ViewModel() {
    private val _stateFlow = MutableStateFlow(
        RecordingContract.State(
            countdown = null,
            metronome = null
        )
    )

    override fun initialize() {
        val permissionState = permissionManager.checkPermissionState(PermissionManager.Permission.RECORD_AUDIO)
        val isPermissionGranted = permissionState == PermissionManager.PermissionState.GRANTED

        if (isPermissionGranted) {
            record()
        } else {
            askRecordingPermission()
        }
    }

    override val stateFlow: StateFlow<RecordingContract.State> = _stateFlow.asStateFlow()

    override fun record(): Job = viewModelScope.launch {
        val context = key.loopContext

        var tmpCountdown = context.timeSignature.getCountdown()
        val beatsPeriod = context.timeSignature.getTimeWithTempo(context.tempo) / tmpCountdown

        _stateFlow.update { state ->
            state.copy(
                countdown = "$tmpCountdown"
            )
        }


        while (tmpCountdown > 0) {
            _stateFlow.update { state ->
                state.copy(
                    countdown = "$tmpCountdown"
                )
            }

            tmpCountdown--
            delay(beatsPeriod.toLong())
        }

        // Launch in parallel for minimum latency
        launch {
            withContext(Dispatchers.Main) {
                _stateFlow.update { state ->
                    state.copy(
                        countdown = null,
                        metronome = RecordingContract.State.Metronome(
                            periodMillis = beatsPeriod.toLong(),
                        )
                    )
                }
            }
        }

        launch {
            withContext(Dispatchers.IO) {
                val recordedTrack = context.record()

                _stateFlow.update { state ->
                    state.copy(
                        countdown = null,
                        metronome = null
                    )
                }

                context.setTrack(key.trackNumber, recordedTrack)
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
}