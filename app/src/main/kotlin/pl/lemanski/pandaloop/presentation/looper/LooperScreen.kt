package pl.lemanski.pandaloop.presentation.looper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperContract
import pl.lemanski.pandaloop.presentation.looper.components.TrackCard
import pl.lemanski.pandaloop.presentation.visual.icons.utils.toImageVector

@Composable
fun LooperScreen(
    playbackButton: Component.IconButton,
    tempo: String,
    timeSignature: String,
    trackCards: List<LooperContract.State.TrackCard>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(text = tempo)
            Text(text = timeSignature)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                trackCards.forEach { TrackCard(it) }
            }

            IconButton(onClick = playbackButton.onClick) {
                Icon(
                    imageVector = playbackButton.icon.toImageVector(),
                    contentDescription = null
                )
            }
        }
    }
}