package pl.lemanski.pandaloop.presentation.looper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.lemanski.pandaloop.domain.PermissionManager
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperViewModel

@Composable
fun LooperRouter() {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val permissionManager = DependencyResolver.resolve<PermissionManager>()

            @Suppress("UNCHECKED_CAST")
            return LooperViewModel(permissionManager) as T
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
}