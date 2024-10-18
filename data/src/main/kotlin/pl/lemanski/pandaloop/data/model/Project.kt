package pl.lemanski.pandaloop.data.model

data class Project(
    val name: String,
    val tempo: Tempo,
    val metrum: Metrum,
    val measures: Int,
    val tracks: List<Track>
)
