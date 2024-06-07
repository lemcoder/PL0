package pl.lemanski.pandaloop.presentation.looper

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.viewModel.looper.LooperContract
import pl.lemanski.pandaloop.presentation.looper.components.TrackCard
import pl.lemanski.pandaloop.presentation.visual.icons.utils.toImageVector
import pl.lemanski.pandaloop.presentation.visual.theme.PandaTheme

@Composable
fun LooperScreen(
    playbackButton: Component.IconButton?,
    recordButton: Component.IconButton?,
    trackCards: List<LooperContract.State.TrackCard>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    trackCards.forEach {
                        TrackCard(it)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                playbackButton?.let {
                    Surface(
                        onClick = playbackButton.onClick,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        shadowElevation = 10.dp,
                        modifier = Modifier.size(100.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = playbackButton.icon.toImageVector(),
                                modifier = Modifier.size(50.dp),
                                contentDescription = null
                            )
                        }

                    }
                }

                recordButton?.let {

                    Surface(
                        onClick = recordButton.onClick,
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        shape = CircleShape,
                        shadowElevation = 10.dp,
                        modifier = Modifier.size(100.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = recordButton.icon.toImageVector(),
                                modifier = Modifier.size(50.dp),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(device = "spec:parent=pixel_8")
@Composable
fun LooperScreenPreview() {
    PandaTheme(darkTheme = true) {
        LooperScreen(
            playbackButton = Component.IconButton(icon = IconResource.PLAY_ARROW, onClick = {}),
            recordButton = Component.IconButton(icon = IconResource.PLUS_SIGN, onClick = {}),
            trackCards = listOf(
                LooperContract.State.TrackCard(
                    id = 1,
                    name = "Track 1",
                    onRemoveClick = {},
                ),
                LooperContract.State.TrackCard(
                    id = 1,
                    name = "Track 2",
                    onRemoveClick = {},
                ),
                LooperContract.State.TrackCard(
                    id = 1,
                    name = "Track 3",
                    onRemoveClick = {},
                ),
                LooperContract.State.TrackCard(
                    id = 1,
                    name = "Track 4",
                    onRemoveClick = {},
                ),

                )
        )
    }
}