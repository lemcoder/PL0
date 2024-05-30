package pl.lemanski.pandaloop.domain.model.navigation

import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.domain.repository.loop.LoopContext

sealed interface Destination {
    data object StartScreen : Destination

    data class RecordingScreen(
        val loopContext: LoopContext,
        val trackNumber: Int
    ) : Destination

    data class LoopScreen(
        val tempo: Int,
        val measures: Int,
        val timeSignature: TimeSignature
    ) : Destination

    data class EffectsScreen(val trackNumber: Int) : Destination
}