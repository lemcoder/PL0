package pl.lemanski.pandaloop.domain.model

import pl.lemanski.pandaloop.TimeSignature
import java.util.UUID

typealias FilePath = String
typealias TrackNumber = Int

data class LoopRecord(
    val id: UUID,
    val name: String,
    val createdTimestamp: Long,
    val tracks: Map<TrackNumber, UUID?>,
    val tempo: Int,
    val timeSignature: TimeSignature
)
