package pl.lemanski.pandaloop.domain.viewModel.start

import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface StartContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun onTempoChanged(tempo: Int)
        fun onTimeSignatureChanged(timeSignature: String)
        fun onCreateLoopClicked()
    }

    data class State(
        val tempoPicker: Component.TempoPicker,
        val timeSignatureSelect: TimeSignatureSelect,
        val createLoopButton: Component.Button
    ) {
        data class TimeSignatureSelect(
            val label: String,
            val options: List<String>,
            val selected: String,
            val onSelectedChanged: (String) -> Unit
        )
    }
}