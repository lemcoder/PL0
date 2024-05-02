package pl.lemanski.pandaloop.domain.navigation

import pl.lemanski.pandaloop.TimeSignature
import pl.lemanski.pandaloop.domain.model.TrackNumber

sealed interface Destination {
    data object StartScreen : Destination

    data class RecordingScreen(
        val trackNumber: TrackNumber
    ) : Destination

    data class LoopScreen(
        var tracks: Map<TrackNumber, ByteArray>,
        val tempo: Int,
        val timeSignature: TimeSignature
    ) : Destination
}