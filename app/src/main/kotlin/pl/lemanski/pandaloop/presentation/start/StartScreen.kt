package pl.lemanski.pandaloop.presentation.start

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.viewModel.start.StartContract
import pl.lemanski.pandaloop.presentation.start.components.TimeSignatureSelect
import pl.lemanski.pandaloop.presentation.visual.components.utils.Composable
import pl.lemanski.pandaloop.presentation.visual.theme.PandaTheme

@Composable
fun StartScreen(
    tempoPicker: Component.TempoPicker,
    timeSignatureSelect: StartContract.State.TimeSignatureSelect,
    createLoopButton: Component.Button
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Surface(
            modifier = Modifier
                .clip(CircleShape)
                .size(160.dp),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 8.dp
        ) {
            TimeSignatureSelect(timeSignatureSelect)
        }

        Surface(
            modifier = Modifier
                .clip(CircleShape)
                .size(200.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentColor = MaterialTheme.colorScheme.primary,
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 8.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                tempoPicker.Composable()
            }
        }


        Button(
            onClick = createLoopButton.onClick,
            modifier = Modifier.size(100.dp)
        ) {
            Text(text = createLoopButton.text)
        }
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    var tempo by remember { mutableIntStateOf(0) }
    var selected by remember { mutableStateOf("") }

    PandaTheme(
        darkTheme = false
    ) {
        Surface {
            StartScreen(
                tempoPicker = Component.TempoPicker(
                    label = "Tempo",
                    tempo = tempo,
                    onTempoChanged = { tempo = it }
                ),
                timeSignatureSelect = StartContract.State.TimeSignatureSelect(
                    label = "Time signature",
                    selected = selected,
                    onSelectedChanged = { selected = it },
                    options = listOf(
                        "4/4",
                        "3/4"
                    )
                ),
                createLoopButton = Component.Button(
                    text = "LOOP",
                    onClick = {}
                )
            )
        }
    }
}