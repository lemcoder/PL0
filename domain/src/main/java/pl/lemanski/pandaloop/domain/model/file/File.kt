package pl.lemanski.pandaloop.domain.model.file

data class File(
    val path: String,
) {
    val name = path.substringAfterLast('/')
}