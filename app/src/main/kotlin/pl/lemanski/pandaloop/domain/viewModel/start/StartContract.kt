package pl.lemanski.pandaloop.domain.viewModel.start

import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface StartContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun onTempoChanged(tempo: Int)
        fun onTimeSignatureChanged(timeSignature: String)
        fun onCreateLoopClicked()
    }

    data class State(
        val tempoPicker: TempoPicker,
        val timeSignatureSelect: TimeSignatureSelect
    ) {
        data object TempoPicker // TODO

        data object TimeSignatureSelect // TODO
    }
}