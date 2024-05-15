package pl.lemanski.pandaloop.presentation.recording

import androidx.compose.runtime.Composable
import pl.lemanski.pandaloop.domain.viewModel.recording.RecordingContract
import pl.lemanski.pandaloop.presentation.recording.components.CountdownScrim

@Composable
fun RecordingScreen(
    countdownScrim: RecordingContract.State.CountdownScrim
) {
    CountdownScrim(state = countdownScrim)
}