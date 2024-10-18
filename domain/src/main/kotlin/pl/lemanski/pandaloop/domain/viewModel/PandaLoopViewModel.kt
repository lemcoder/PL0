package pl.lemanski.pandaloop.domain.viewModel

import kotlinx.coroutines.flow.StateFlow

interface PandaLoopViewModel<STATE> {
    val stateFlow: StateFlow<STATE>
}