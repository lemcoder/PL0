package pl.lemanski.pandaloop.domain.model

import pl.lemanski.pandaloop.TimeSignature
import java.util.UUID

data class TrackRecord(
    val id: UUID,
    val path: String,
    val tempo: Int,
    val timeSignature: TimeSignature
)
