package pl.lemanski.pandaloop.presentation.sequencer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.model.exceptions.NavigationStateException
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.key
import pl.lemanski.pandaloop.domain.viewModel.sequencer.SequencerViewModel

@Composable
fun SequencerRouter() {
    val viewModel: SequencerViewModel = viewModel(factory = rememberViewModelFactory())
    val state by viewModel.stateFlow.collectAsState()

    SequencerScreen(
        text = state.text
    )
}

@Composable
private fun rememberViewModelFactory(): ViewModelProvider.Factory = remember {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val navigationService = DependencyResolver.resolve<NavigationService>()
            val recordingNavigationKey = navigationService.key<Destination.SequencerScreen>() ?: throw NavigationStateException("Navigation key is null")

            @Suppress("UNCHECKED_CAST")
            return SequencerViewModel(
                navigationService = navigationService,
                key = recordingNavigationKey,
            ) as T
        }
    }
}