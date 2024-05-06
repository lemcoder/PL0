package pl.lemanski.pandaloop.presentation.looper.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperContract
import pl.lemanski.pandaloop.presentation.visual.icons.utils.toImageVector

@Composable
fun TrackCard(
    state: LooperContract.State.TrackCard
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = if (state.isEmpty) "empty" else "full")
                Text(text = state.name)
            }

            when (state.isEmpty) {
                true  -> {
                    IconButton(onClick = { state.onRecordClick(state.id) }) {
                        Icon(
                            imageVector = IconResource.REC_DOT.toImageVector(),
                            contentDescription = "trash bin"
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