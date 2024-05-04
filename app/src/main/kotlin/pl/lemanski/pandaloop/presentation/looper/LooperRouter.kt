package pl.lemanski.pandaloop.presentation.looper

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
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperViewModel

@Composable
fun LooperRouter() {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val navigationController = DependencyResolver.resolve<NavigationController>()
            val key = navigationController.keyOfType<Destination.LoopScreen>() ?: throw NavigationStateException()

            @Suppress("UNCHECKED_CAST")
            return LooperViewModel(
                navigationController = navigationController,
                key = key,
            ) as T
        }
    }

    val viewModel: LooperViewModel = viewModel(factory = factory)
    val state by viewModel.stateFlow.collectAsState()

    LooperScreen(
        playbackButton = state.playbackButton,
        tempo = state.tempo,
        timeSignature = state.timeSignature,
        trackCards = state.tracks
    )

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
}