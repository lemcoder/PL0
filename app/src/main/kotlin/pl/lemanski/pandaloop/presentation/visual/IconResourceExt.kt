package pl.lemanski.pandaloop.presentation.visual

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import pl.lemanski.pandaloop.domain.model.visual.IconResource

fun IconResource.toImageVector(): ImageVector = when (this) {
    IconResource.PLAY_ARROW   -> Icons.Default.PlayArrow
    IconResource.PAUSE_BARS   -> Icons.Default.Clear
    IconResource.PLUS_SIGN    -> Icons.Default.Add
    IconResource.MINUS_SIGN   -> Icons.Default.Menu // TODO
    IconResource.REC_DOT      -> Icons.Default.AddCircle // TODO
    IconResource.TRASH_BIN    -> Icons.Default.Delete
    IconResource.SPEAKER_PLAY -> Icons.Default.Favorite
    IconResource.SPEAKER_MUTE -> Icons.Default.FavoriteBorder
}