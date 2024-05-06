package pl.lemanski.pandaloop.presentation.recording.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.viewModel.recording.RecordingContract

@Composable
fun CountdownScrim(
    state: RecordingContract.State.CountdownScrim,
) {
    if (!state.isRecording) {
        AnimatedContent(
            targetState = state.currentCount,
            label = "Countdown",
            transitionSpec = { scaleIn() + fadeIn() togetherWith fadeOut() }
        ) { target ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = target.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { state.countdown.toFloat() / state.currentCount.toFloat() },
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
