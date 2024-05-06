package pl.lemanski.pandaloop.presentation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.platform.PermissionManager
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.viewModel.start.StartViewModel

@Composable
fun StartRouter() {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val permissionManager = DependencyResolver.resolve<PermissionManager>()
            val navigationController = DependencyResolver.resolve<NavigationController>()
            val localization = DependencyResolver.resolve<Localization>()

            @Suppress("UNCHECKED_CAST")
            return StartViewModel(
                permissionManager = permissionManager,
                navigationController = navigationController,
                localization = localization
            ) as T
        }
    }

    val viewModel: StartViewModel = viewModel(factory = factory)
    val state by viewModel.stateFlow.collectAsState()

    StartScreen(
        tempoPicker = state.tempoPicker,
        timeSignatureSelect = state.timeSignatureSelect,
        createLoopButton = state.createLoopButton
    )
}