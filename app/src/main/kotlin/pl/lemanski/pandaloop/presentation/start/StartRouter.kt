package pl.lemanski.pandaloop.presentation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.viewModel.start.StartViewModel

@Composable
fun StartRouter() {
    val viewModel: StartViewModel = viewModel(factory = rememberViewModelFactory())
    val state by viewModel.stateFlow.collectAsState()

    StartScreen(
        tempoPicker = state.tempoPicker,
        timeSignatureSelect = state.timeSignatureSelect,
        createLoopButton = state.createLoopButton,
        measuresPicker = state.measuresPicker
    )
}

@Composable
private fun rememberViewModelFactory(): ViewModelProvider.Factory = remember {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val navigationService = DependencyResolver.resolve<NavigationService>()
            val localization = DependencyResolver.resolve<Localization>()

            @Suppress("UNCHECKED_CAST")
            return StartViewModel(
                navigationService = navigationService,
                localization = localization
            ) as T
        }
    }
}