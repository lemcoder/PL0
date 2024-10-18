package pl.lemanski.pandaloop.domain.model.navigation

import kotlinx.serialization.Serializable
import pl.lemanski.pandaloop.domain.model.timeSignature.TimeSignature
import pl.lemanski.pandaloop.domain.repository.loop.LoopContext

sealed interface Destination

@Serializable
data object StartScreen : Destination

data class RecordingScreen(
    val loopContext: LoopContext,
    val trackNumber: Int
) : Destination

@Serializable
data class LoopScreen(val tempo: Int, val timeSignature: TimeSignature, val measures: Int) : Destination

data class SequencerScreen(val loopContext: LoopContext) : Destination

data class EffectsScreen(val trackNumber: Int) : Destination