package pl.lemanski.pandaloop.domain.model.track

import pl.lemanski.pandaloop.domain.model.effect.Effect

data class Track(
    val name: String,
    val data: ByteArray,
    val effects: List<Effect>
)