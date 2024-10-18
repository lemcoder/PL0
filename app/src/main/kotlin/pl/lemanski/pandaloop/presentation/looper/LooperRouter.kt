package pl.lemanski.pandaloop.presentation.looper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.model.exceptions.NavigationStateException
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.model.navigation.LoopScreen
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.key
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperViewModel

@Composable
fun LooperRouter() {
    val viewModel: LooperViewModel = viewModel(factory = rememberViewModelFactory())
    val state by viewModel.stateFlow.collectAsState()

    LooperScreen(
        playbackButton = state.playbackButton,
        recordButton = state.recordButton,
        trackCards = state.tracks
    )

    // This will be called on every recomposition but it is fine
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
}

@Composable
private fun rememberViewModelFactory(): ViewModelProvider.Factory = remember {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val navigationService = DependencyResolver.resolve<NavigationService>()
            val key = navigationService.key<LoopScreen>() ?: throw NavigationStateException("Key not found: ${LoopScreen::class}")

            @Suppress("UNCHECKED_CAST")
            return LooperViewModel(
                navigationService = navigationService,
                key = key,
            ) as T
        }
    }
}
