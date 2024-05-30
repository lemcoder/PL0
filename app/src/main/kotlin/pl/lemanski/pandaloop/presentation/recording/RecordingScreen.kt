package pl.lemanski.pandaloop.presentation.recording

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pl.lemanski.pandaloop.domain.viewModel.recording.RecordingContract
import pl.lemanski.pandaloop.presentation.visual.theme.PandaTheme
import kotlin.math.roundToInt

@Composable
fun RecordingScreen(
    countdown: String?,
    metronome: RecordingContract.State.Metronome?,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        countdown?.let {
            CountdownScreen(it)
        }

        metronome?.let {
            MetronomeScreen(it)
        }
    }
}

@Composable
fun CountdownScreen(
    countdown: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = countdown,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun MetronomeScreen(
    state: RecordingContract.State.Metronome,
) {
    var degrees by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .rotate(degrees)
                .size(100.dp)
                .border(5.dp, MaterialTheme.colorScheme.inversePrimary, RectangleShape)
        ) {
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(state.periodMillis)
            degrees = (degrees + 45) % 90
        }
    }
}

@Preview
@Composable
fun MetronomePreview() {
    PandaTheme {
        MetronomeScreen(RecordingContract.State.Metronome(1000))
    }
}