package pl.lemanski.pandaloop.presentation.visual.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import pl.lemanski.pandaloop.domain.model.visual.Component

@Composable
fun ButtonComponent(
    state: Component.Button
) {
    Button(onClick = state.onClick) {
        Text(text = state.text)
    }
}