package pl.lemanski.pandaloop.domain.viewModel.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.lemanski.pandaloop.domain.model.navigation.LoopScreen
import pl.lemanski.pandaloop.domain.model.timeSignature.TimeSignature
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.platform.log.Logger
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.goTo
import pl.lemanski.pandaloop.domain.utils.Debounce

class StartViewModel(
    private val navigationService: NavigationService,
    private val localization: Localization
) : StartContract.ViewModel, ViewModel() {
    private val logger = Logger.get(this::class)
    private val debouncedOnCreateButtonClick = Debounce(::onCreateLoopClicked)
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
                onClick = { debouncedOnCreateButtonClick(1_000) }
            ),
            measuresPicker = Component.MeasuresPicker(
                label = localization.measures,
                measures = 4,
                onMeasuresChanged = ::onMeasuresChanged
            )
        )
    )

    init {
        logger.i { "Initialized" }
    }

    override val stateFlow: StateFlow<StartContract.State> = _stateFlow.asStateFlow()

    override fun onTempoChanged(tempo: Int) {
        if (tempo !in 30..180) return

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
        if (measures !in 1..16) return

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
            LoopScreen(
                tempo = tempo,
                timeSignature = timeSignature,
                measures = measures
            )
        )
    }
}