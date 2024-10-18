package pl.lemanski.pandaloop.domain.viewModel.sequencer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import pl.lemanski.pandaloop.domain.model.navigation.SequencerScreen
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService

class SequencerViewModel(
    private val navigationService: NavigationService,
    private val key: SequencerScreen,
) : SequencerContract.ViewModel, ViewModel() {
    override fun record(): Job {
        TODO("Not yet implemented")
    }

    override val stateFlow: StateFlow<SequencerContract.State>
        get() = TODO("Not yet implemented")
}