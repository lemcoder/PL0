package pl.lemanski.pandaloop.domain.utils

import pl.lemanski.pandaloop.TimeSignature
import pl.lemanski.pandaloop.getBufferSizeInBytesWithTempo

fun TimeSignature.emptyBuffer(tempo: Int): ByteArray = ByteArray(this.getBufferSizeInBytesWithTempo(tempo).toInt())
