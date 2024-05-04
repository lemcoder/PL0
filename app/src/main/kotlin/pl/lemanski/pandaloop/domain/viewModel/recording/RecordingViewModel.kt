package pl.lemanski.pandaloop.domain.viewModel.recording

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pl.lemanski.pandaloop.Recording
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.getTimeWithTempo

class RecordingViewModel(
    private val navigationController: NavigationController,
    private val key: Destination.RecordingScreen
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
                label = "TODO label",
                options = Countdown.entries.map { it.name },
                onSelectedChanged = ::onCountdownChanged,
                selected = Countdown.FOUR.name
            ),
            countdownScrim = RecordingContract.State.CountdownScrim(
                visible = false,
                text = ""
            )
        )
    )

    override val stateFlow: StateFlow<RecordingContract.State> = _stateFlow.asStateFlow()

    override fun onRecordClick(): Job = viewModelScope.launch {
        if (recordingLock.isLocked) return@launch

        recordingLock.withLock {
            val currentCountdown = Countdown.valueOf(_stateFlow.value.countdownSelect.selected).count
            val recordingTime = key.timeSignature.getTimeWithTempo(key.tempo)
            val newRecording = Recording(recordingTime.toInt())

            var countdown = currentCountdown
            while (countdown > 0) {
                _stateFlow.update { state ->
                    state.copy(
                        countdownScrim = state.countdownScrim.copy(
                            visible = true,
                            text = "$countdown"
                        )
                    )
                }
                delay(recordingTime / currentCountdown)
                countdown--
            }

            _stateFlow.update { state ->
                state.copy(
                    countdownScrim = state.countdownScrim.copy(
                        visible = true,
                        text = "TODO recording..."
                    )
                )
            }
            newRecording.start()
            delay(recordingTime + 200L) // Add some padding
            newRecording.stop()

            val buffer = newRecording.recordedBuffer
            val looperKey = navigationController.keyOfType<Destination.LoopScreen>() ?: return@launch
            looperKey.tracks[key.trackNumber] = buffer

            newRecording.close()
            navigationController.goBack()
        }
    }

    override fun onCountdownChanged(count: String) {
        _stateFlow.update { state ->
            state.copy(
                countdownSelect = _stateFlow.value.countdownSelect.copy(selected = count)
            )
        }
    }
}