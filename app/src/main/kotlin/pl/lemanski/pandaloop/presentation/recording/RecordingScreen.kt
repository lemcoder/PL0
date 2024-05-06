package pl.lemanski.pandaloop.presentation.recording

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.viewModel.recording.RecordingContract
import pl.lemanski.pandaloop.presentation.recording.components.CountdownScrim
import pl.lemanski.pandaloop.presentation.visual.components.utils.Composable

@Composable
fun RecordingScreen(
    recordButton: Component.IconButton,
    countdownSelect: Component.TextSelect,
    countdownScrim: RecordingContract.State.CountdownScrim
) {
    if (countdownScrim.visible) {
        CountdownScrim(state = countdownScrim)
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                recordButton.Composable()
                countdownSelect.Composable()
            }
        }
    }
}