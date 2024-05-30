package pl.lemanski.pandaloop.presentation.looper

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
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.key
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperViewModel

@Composable
fun LooperRouter() {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val navigationService = DependencyResolver.resolve<NavigationService>()
            val key = navigationService.key<Destination.LoopScreen>() ?: throw NavigationStateException("Key not found: ${Destination.LoopScreen::class}")

            @Suppress("UNCHECKED_CAST")
            return LooperViewModel(
                navigationService = navigationService,
                key = key,
            ) as T
        }
    }

    val viewModel: LooperViewModel = viewModel(factory = factory)
    val state by viewModel.stateFlow.collectAsState()

    LooperScreen(
        playbackButton = state.playbackButton,
        recordButton = state.recordButton,
        trackCards = state.tracks
    )

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
}