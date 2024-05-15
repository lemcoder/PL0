package pl.lemanski.pandaloop.presentation.looper.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperContract
import pl.lemanski.pandaloop.presentation.visual.icons.utils.toImageVector
import pl.lemanski.pandaloop.presentation.visual.theme.PandaTheme

@Composable
fun TrackCard(
    state: LooperContract.State.TrackCard
) {
    Surface(
        shape = RoundedCornerShape(50.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(30.dp)
            ) {
                Text(text = state.name)
            }

            Surface(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.inverseSurface),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(80.dp),
                onClick = { state.onRecordClick(state.id) }
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = IconResource.EFFECTS.toImageVector(),
                        contentDescription = "trash bin"
                    )
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(100.dp)
            ) {
                when (state.isEmpty) {
                    true -> {
                        IconButton(onClick = { state.onRecordClick(state.id) }) {
                            Icon(
                                imageVector = IconResource.PLUS_SIGN.toImageVector(),
                                contentDescription = "trash bin",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    false -> {
                        IconButton(onClick = { state.onRemoveClick(state.id) }) {
                            Icon(
                                imageVector = IconResource.TRASH_BIN.toImageVector(),
                                contentDescription = "trash bin"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTrackCard() {
    var isEmpty by remember { mutableStateOf(true) }

    PandaTheme(darkTheme = true) {
        TrackCard(
            state = LooperContract.State.TrackCard(
                id = 1,
                name = "Track 1",
                isEmpty = isEmpty,
                onRemoveClick = { isEmpty = true },
                onRecordClick = { isEmpty = false },
                onEffectClick = { }
            )
        )
    }
}