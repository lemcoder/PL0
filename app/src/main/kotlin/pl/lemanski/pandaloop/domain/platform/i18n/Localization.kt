package pl.lemanski.pandaloop.domain.platform.i18n

interface Localization {
    val timeSignature: String
    val tempo: String
    val loop: String
    val recording: String
    val countdown: String
    fun measures(beats: Int): String
}