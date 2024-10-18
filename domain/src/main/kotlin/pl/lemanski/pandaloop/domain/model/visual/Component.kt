package pl.lemanski.pandaloop.domain.model.visual

sealed interface Component {
    data class Button(
        val text: String,
        val onClick: () -> Unit
    ) : Component

    data class IconButton(
        val icon: IconResource,
        val onClick: () -> Unit
    ) : Component

    data class TempoPicker(
        val label: String,
        val tempo: Int,
        val onTempoChanged: (Int) -> Unit
    ) : Component

    data class TextSelect(
        val label: String,
        val selected: Int,
        val onSelectedChanged: (Int) -> Unit,
        val options: List<Option>,
    ) : Component {
        data class Option(
            val label: String,
            val value: Int
        )
    }

    data class MeasuresPicker(
        val label: String,
        val measures: Int,
        val onMeasuresChanged: (Int) -> Unit
    ) : Component
}