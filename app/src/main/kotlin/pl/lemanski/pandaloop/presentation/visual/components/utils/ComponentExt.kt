package pl.lemanski.pandaloop.presentation.visual.components.utils

import androidx.compose.runtime.Composable
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.presentation.visual.components.ButtonComponent
import pl.lemanski.pandaloop.presentation.visual.components.IconButtonComponent
import pl.lemanski.pandaloop.presentation.visual.components.MeasuresPickerComponent
import pl.lemanski.pandaloop.presentation.visual.components.TempoPickerComponent
import pl.lemanski.pandaloop.presentation.visual.components.TextSelectComponent

@Composable
fun Component.Composable(): Unit = when (this) {
    is Component.Button         -> ButtonComponent(state = this)
    is Component.IconButton     -> IconButtonComponent(state = this)
    is Component.TempoPicker    -> TempoPickerComponent(state = this)
    is Component.TextSelect     -> TextSelectComponent(state = this)
    is Component.MeasuresPicker -> MeasuresPickerComponent(state = this)
}