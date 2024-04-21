package pl.lemanski.pandaloop.domain.viewModel.looper

import kotlinx.coroutines.Job
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface LooperContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun onButtonClick(): Job
    }

    data class State(
        val text: String,
        val onButtonClick: () -> Unit
    )
}