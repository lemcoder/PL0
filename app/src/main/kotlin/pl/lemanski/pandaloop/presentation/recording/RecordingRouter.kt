package pl.lemanski.pandaloop.presentation.recording

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.exceptions.NavigationStateException
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.platform.PermissionManager
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.viewModel.recording.RecordingViewModel

@Composable
fun RecordingRouter() {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val permissionManager = DependencyResolver.resolve<PermissionManager>()
            val navigationController = DependencyResolver.resolve<NavigationController>()
            val recordingNavigationKey = navigationController.keyOfType<Destination.RecordingScreen>() ?: throw NavigationStateException()
            val localization = DependencyResolver.resolve<Localization>()

            @Suppress("UNCHECKED_CAST")
            return RecordingViewModel(
                permissionManager = permissionManager,
                navigationController = navigationController,
                key = recordingNavigationKey,
                localization = localization
            ) as T
        }
    }

    val viewModel: RecordingViewModel = viewModel(factory = factory)
    val state by viewModel.stateFlow.collectAsState()

    RecordingScreen(
        countdownScrim = state.countdownScrim
    )

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
}