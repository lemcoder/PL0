package pl.lemanski.pandaloop.presentation.recording

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.model.exceptions.NavigationStateException
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.platform.permission.PermissionManager
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.key
import pl.lemanski.pandaloop.domain.viewModel.recording.RecordingViewModel

@Composable
fun RecordingRouter() {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val permissionManager = DependencyResolver.resolve<PermissionManager>()
            val navigationService = DependencyResolver.resolve<NavigationService>()
            val recordingNavigationKey = navigationService.key<Destination.RecordingScreen>() ?: throw NavigationStateException("Navigation key is null")

            @Suppress("UNCHECKED_CAST")
            return RecordingViewModel(
                permissionManager = permissionManager,
                navigationService = navigationService,
                key = recordingNavigationKey,
            ) as T
        }
    }

    val viewModel: RecordingViewModel = viewModel(factory = factory)
    val state by viewModel.stateFlow.collectAsState()

    RecordingScreen(
        countdown = state.countdown,
        metronome = state.metronome
    )

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
}