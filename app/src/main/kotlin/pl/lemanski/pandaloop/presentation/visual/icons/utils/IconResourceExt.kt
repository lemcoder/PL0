package pl.lemanski.pandaloop.presentation.visual.icons.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.ImageVector
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.presentation.visual.icons.IcPauseCircle
import pl.lemanski.pandaloop.presentation.visual.icons.IcPlayCircle
import pl.lemanski.pandaloop.presentation.visual.icons.IcRemove
import pl.lemanski.pandaloop.presentation.visual.icons.IcSpeakerMute
import pl.lemanski.pandaloop.presentation.visual.icons.IcSpeakerPlay

fun IconResource.toImageVector(): ImageVector = when (this) {
    IconResource.PLAY_ARROW   -> IcPlayCircle
    IconResource.PAUSE_BARS   -> IcPauseCircle
    IconResource.PLUS_SIGN    -> Icons.Default.Add
    IconResource.MINUS_SIGN   -> IcRemove
    IconResource.REC_DOT      -> Icons.Default.AddCircle
    IconResource.TRASH_BIN    -> Icons.Default.Delete
    IconResource.SPEAKER_PLAY -> IcSpeakerPlay
    IconResource.SPEAKER_MUTE -> IcSpeakerMute
}