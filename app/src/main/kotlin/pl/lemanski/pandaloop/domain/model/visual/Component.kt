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
        val selected: String,
        val onSelectedChanged: (String) -> Unit,
        val options: List<String>,
    ) : Component
}