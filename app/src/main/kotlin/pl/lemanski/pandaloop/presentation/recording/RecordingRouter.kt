package pl.lemanski.pandaloop.presentation.recording

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.exceptions.NavigationStateException
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.viewModel.recording.RecordingViewModel

@Composable
fun RecordingRouter() {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            val navigationController = DependencyResolver.resolve<NavigationController>()
            val recordingNavigationKey = navigationController.keyOfType<Destination.RecordingScreen>() ?: throw NavigationStateException()

            @Suppress("UNCHECKED_CAST")
            return RecordingViewModel(
                navigationController = navigationController,
                key = recordingNavigationKey
            ) as T
        }
    }

    val viewModel: RecordingViewModel = viewModel(factory = factory)
    val state by viewModel.stateFlow.collectAsState()

    RecordingScreen()
}