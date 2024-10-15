package pl.lemanski.pandaloop.domain.viewModel.sequencer

import kotlinx.coroutines.Job
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface SequencerContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun record(): Job
    }

    data class State(
        val text: String
    )
}