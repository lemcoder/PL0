package pl.lemanski.pandaloop.presentation.visual.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.presentation.visual.icons.utils.toImageVector

@Composable
fun IconButtonComponent(
    state: Component.IconButton
) {
    IconButton(onClick = state.onClick) {
        Icon(
            imageVector = state.icon.toImageVector(),
            contentDescription = null
        )
    }
}