package pl.lemanski.pandaloop.presentation.looper.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
        modifier = Modifier.height(100.dp),
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
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(100.dp),
                onClick = { state.onRemoveClick(state.id) }
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = IconResource.TRASH_BIN.toImageVector(),
                        contentDescription = "trash bin"
                    )
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
                onRemoveClick = { isEmpty = true },
            )
        )
    }
}