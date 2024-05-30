package pl.lemanski.pandaloop.domain.viewModel.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.goTo

class StartViewModel(
    private val navigationService: NavigationService,
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
            ),
            measuresPicker = Component.MeasuresPicker(
                label = localization.measures,
                measures = 4,
                onMeasuresChanged = ::onMeasuresChanged
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

    override fun onMeasuresChanged(measures: Int) {
        if (measures > 32 || measures < 1) return

        _stateFlow.update { state ->
            state.copy(
                measuresPicker = _stateFlow.value.measuresPicker.copy(
                    measures = measures
                )
            )
        }
    }

    override fun onCreateLoopClicked() {
        val tempo = _stateFlow.value.tempoPicker.tempo
        val timeSignature = TimeSignature.valueOf(_stateFlow.value.timeSignatureSelect.selected)
        val measures = _stateFlow.value.measuresPicker.measures

        navigationService.goTo(
            Destination.LoopScreen(
                tempo = tempo,
                timeSignature = timeSignature,
                measures = measures
            )
        )
    }
}