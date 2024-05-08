package pl.lemanski.pandaloop.domain.navigation

import pl.lemanski.pandaloop.core.TimeSignature


sealed interface Destination {
    data object StartScreen : Destination

    data class RecordingScreen(
        val trackNumber: Int,
        val tempo: Int,
        val timeSignature: TimeSignature
    ) : Destination

    data class LoopScreen(
        var tracks: MutableMap<Int, ByteArray>,
        val tempo: Int,
        val timeSignature: TimeSignature
    ) : Destination
}