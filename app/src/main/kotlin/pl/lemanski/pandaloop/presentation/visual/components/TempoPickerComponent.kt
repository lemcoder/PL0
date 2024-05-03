package pl.lemanski.pandaloop.presentation.visual.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.presentation.visual.toImageVector

@Composable
fun TempoPickerComponent(
    state: Component.TempoPicker
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { state.onTempoChanged(state.tempo - 5) }) {
            Icon(
                imageVector = IconResource.MINUS_SIGN.toImageVector(),
                contentDescription = "Minus sign"
            )
        }

        Text(text = state.tempo.toString())

        IconButton(onClick = { state.onTempoChanged(state.tempo + 5) }) {
            Icon(
                imageVector = IconResource.PLUS_SIGN.toImageVector(),
                contentDescription = "Plus sign"
            )
        }
    }
}