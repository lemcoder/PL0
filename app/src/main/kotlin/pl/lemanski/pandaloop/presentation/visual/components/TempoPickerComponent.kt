package pl.lemanski.pandaloop.presentation.visual.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.presentation.visual.icons.utils.toImageVector
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TempoPickerComponent(
    state: Component.TempoPicker,
    modifier: Modifier = Modifier,
    resetTimeMillis: Long = 2_000L,
) {
    var tappedTempo by remember { mutableIntStateOf(state.tempo) }
    var lastTapMillis by remember { mutableLongStateOf(0L) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Max)
    ) {
        Text(
            text = state.label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(ButtonDefaults.MinHeight)
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        role = Role.Button,
                        onLongClick = { state.onTempoChanged(state.tempo - 1) },
                        onClick = { state.onTempoChanged(state.tempo - 5) }
                    )
            ) {
                Icon(
                    imageVector = IconResource.MINUS_SIGN.toImageVector(),
                    contentDescription = "Minus sign"
                )
            }

            Text(
                text = state.tempo.toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.width(ButtonDefaults.MinWidth),
                textAlign = TextAlign.Center
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(ButtonDefaults.MinHeight)
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        role = Role.Button,
                        onLongClick = { state.onTempoChanged(state.tempo + 1) },
                        onClick = { state.onTempoChanged(state.tempo + 5) }
                    )
            ) {
                Icon(
                    imageVector = IconResource.PLUS_SIGN.toImageVector(),
                    contentDescription = "Plus sign"
                )
            }
        }

        OutlinedButton(
            onClick = {
                val lastTap = lastTapMillis
                val currentMillis = System.currentTimeMillis()

                if (currentMillis - lastTap > resetTimeMillis) {
                    lastTapMillis = currentMillis
                } else {
                    tappedTempo = (1.minutes.inWholeMilliseconds / (currentMillis - lastTap)).toInt().coerceIn(30, 180)
                    lastTapMillis = currentMillis
                    state.onTempoChanged(tappedTempo)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tap")
        }
    }
}

@Preview
@Composable
fun TempoPickerComponentPreview() {
    var tempo by remember { mutableIntStateOf(60) }

    MaterialTheme {
        Scaffold(modifier = Modifier.size(200.dp)) { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                TempoPickerComponent(
                    state = Component.TempoPicker(
                        tempo = tempo,
                        onTempoChanged = { tempo = it },
                        label = "Tempo",
                    ),
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}