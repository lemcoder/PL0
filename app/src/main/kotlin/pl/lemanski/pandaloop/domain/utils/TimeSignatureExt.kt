package pl.lemanski.pandaloop.domain.utils

import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.core.getBufferSizeInBytesWithTempo


fun TimeSignature.emptyBuffer(tempo: Int): ByteArray = ByteArray(this.getBufferSizeInBytesWithTempo(tempo).toInt().coerceIn(0, Int.MAX_VALUE))
