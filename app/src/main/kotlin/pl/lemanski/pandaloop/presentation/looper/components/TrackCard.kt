package pl.lemanski.pandaloop.presentation.looper.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperContract

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
                Text(text = if (state.isEmpty) "empty" else "full" )
                Text(text = state.name)
                Text(text = state.timestamp.toString())
            }

            Column {
                Button(onClick = { state.onRecordClick(state.id) }) {
                    Text(text = "TODO rec")
                }

                Button(onClick = { state.onRemoveClick(state.id) }) {
                    Text(text = "TODO del")
                }
            }
        }
    }
}