package pl.lemanski.pandaloop.presentation.start

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import pl.lemanski.pandaloop.presentation.visual.components.AdaptiveLinearLayout
import pl.lemanski.pandaloop.presentation.visual.components.utils.Composable
import pl.lemanski.pandaloop.presentation.visual.theme.PandaTheme

@Composable
fun StartScreen(
    tempoPicker: Component.TempoPicker,
    timeSignatureSelect: StartContract.State.TimeSignatureSelect,
    createLoopButton: Component.Button,
    measuresPicker: Component.MeasuresPicker
) {
    AdaptiveLinearLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(180.dp),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 8.dp
        ) {
            TimeSignatureSelect(timeSignatureSelect)
        }



        Surface(
            modifier = Modifier
                .size(width = 180.dp, height = 100.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
            contentColor = MaterialTheme.colorScheme.primary,
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 0.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                measuresPicker.Composable()
            }
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
            modifier = Modifier.size(120.dp),
        ) {
            Text(text = createLoopButton.text)
        }
    }
}

@Preview(device = "spec:parent=3.3in WQVGA, orientation=landscape")
@Composable
fun StartScreenPreview() {
    var tempo by remember { mutableIntStateOf(0) }
    var selected by remember { mutableStateOf("") }

    PandaTheme(
        darkTheme = true
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
                ),
                measuresPicker = Component.MeasuresPicker(
                    label = "Measures",
                    measures = 4,
                    onMeasuresChanged = {}
                )
            )
        }
    }
}