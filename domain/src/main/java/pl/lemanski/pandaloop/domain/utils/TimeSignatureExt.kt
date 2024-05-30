package pl.lemanski.pandaloop.domain.utils

import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.core.getBufferSizeInBytesWithTempo
import pl.lemanski.pandaloop.core.getTimeWithTempo

fun TimeSignature.emptyBuffer(tempo: Int, measures: Int): ByteArray = ByteArray(this.getBufferSizeInBytesWithTempo(tempo).toInt().coerceAtLeast(0) * measures)

fun TimeSignature.getBufferSizeInBytes(tempo: Int, measures: Int): Long = this.getBufferSizeInBytesWithTempo(tempo).coerceAtLeast(0) * measures

fun TimeSignature.getTime(tempo: Int, measures: Int): Long = (this.getTimeWithTempo(tempo) * measures).toLong().coerceAtLeast(0)
