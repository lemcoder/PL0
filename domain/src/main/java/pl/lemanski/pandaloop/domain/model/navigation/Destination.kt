package pl.lemanski.pandaloop.domain.model.navigation

import pl.lemanski.pandaloop.domain.repository.loop.LoopContext

sealed interface Destination {
    data object StartScreen : Destination

    data class RecordingScreen(
        val loopContext: LoopContext,
        val trackNumber: Int
    ) : Destination

    data class LoopScreen(val loopContext: LoopContext) : Destination

    data class EffectsScreen(val trackNumber: Int) : Destination
}