package pl.lemanski.pandaloop.domain.viewModel.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.lemanski.pandaloop.TimeSignature
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.utils.emptyBuffer

class StartViewModel(
    private val navigationController: NavigationController,
    private val localization: Localization
) : StartContract.ViewModel, ViewModel() {
    private val _stateFlow = MutableStateFlow(
        StartContract.State(
            tempoPicker = Component.TempoPicker(
                label = localization.tempo,
                tempo = 60,
                onTempoChanged = ::onTempoChanged
            ),
            timeSignatureSelect = StartContract.State.TimeSignatureSelect(
                label = localization.timeSignature,
                options = TimeSignature.entries.map { it.name },
                selected = TimeSignature.COMMON.name,
                onSelectedChanged = ::onTimeSignatureChanged
            ),
            createLoopButton = Component.Button(
                text = localization.loop.uppercase(),
                onClick = ::onCreateLoopClicked
            )
        )
    )

    override val stateFlow: StateFlow<StartContract.State> = _stateFlow.asStateFlow()

    override fun onTempoChanged(tempo: Int) {
        _stateFlow.update { state ->
            state.copy(
                tempoPicker = _stateFlow.value.tempoPicker.copy(
                    tempo = tempo
                )
            )
        }
    }

    override fun onTimeSignatureChanged(timeSignature: String) {
        _stateFlow.update { state ->
            state.copy(
                timeSignatureSelect = _stateFlow.value.timeSignatureSelect.copy(selected = timeSignature)

            )
        }
    }

    override fun onCreateLoopClicked() {
        val tempo = _stateFlow.value.tempoPicker.tempo
        val timeSignature = TimeSignature.valueOf(_stateFlow.value.timeSignatureSelect.selected)

        val emptyBuffer = timeSignature.emptyBuffer(tempo)
        navigationController.goTo(
            Destination.LoopScreen(
                tracks = mutableMapOf(
                    0 to emptyBuffer,
                    1 to emptyBuffer,
                    2 to emptyBuffer,
                    3 to emptyBuffer,
                ),
                tempo = tempo,
                timeSignature = timeSignature
            )
        )
    }
}