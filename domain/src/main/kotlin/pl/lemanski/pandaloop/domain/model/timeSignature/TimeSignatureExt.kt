package pl.lemanski.pandaloop.domain.model.timeSignature

import pl.lemanski.mikroaudio.MikroAudio
import kotlin.math.round
import kotlin.time.Duration.Companion.minutes

internal fun TimeSignature.getTimeWithTempo(tempo: Int): Double {
    val beat = 1.minutes.inWholeMilliseconds.toDouble() / tempo
    return when (this) {
        TimeSignature.COMMON -> (4.0 * beat)
        TimeSignature.THREE_FOURS -> (3.0 * beat)
    }
}

internal fun MikroAudio.getBufferSizeInBytesWithTempo(timeSignature: TimeSignature, tempo: Int): Long {
    val timeInMs = timeSignature.getTimeWithTempo(tempo)
    val bytesPerFrame = 4 // F32 always 4 bytes

    val bufferSize = round(
        audioEngine.options.sampleRate.toDouble() * audioEngine.options.channelCount * bytesPerFrame * (timeInMs / 1000.0)
    ).toLong()

    return bufferSize - (bufferSize % bytesPerFrame) // ensure buffer size is multiple of 4 bytes
}

/**
 * Get empty buffer for given time signature and tempo
 *
 * @param timeSignature time signature to use for calculation
 * @param tempo tempo to use for calculation
 * @param measures number of measures to use for calculation
 *
 * @return empty buffer of given size
 */
fun MikroAudio.emptyBuffer(timeSignature: TimeSignature, tempo: Int, measures: Int = 1): ByteArray = ByteArray(this.getBufferSizeInBytesWithTempo(timeSignature, tempo).toInt().coerceAtLeast(0) * measures)

/**
 * Get buffer size in bytes for given time signature, tempo and number of measures
 *
 * @param timeSignature time signature to use for calculation
 * @param tempo tempo to use for calculation
 * @param measures number of measures to use for calculation
 *
 * @return buffer size in bytes
 */
fun MikroAudio.getBufferSizeInBytes(timeSignature: TimeSignature, tempo: Int, measures: Int = 1): Long {
    val oneMeasureBufferSize = this.getBufferSizeInBytesWithTempo(timeSignature, tempo).coerceAtLeast(0)
    val fullBufferSize = oneMeasureBufferSize * measures
    return fullBufferSize - (fullBufferSize % 4)
}

/**
 * Get time in milliseconds for given time signature and tempo
 *
 * @param tempo tempo to use for calculation
 * @param measures number of measures to use for calculation
 *
 * @return time in milliseconds
 */
fun TimeSignature.getTime(tempo: Int, measures: Int = 1): Long = (this.getTimeWithTempo(tempo) * measures).toLong().coerceAtLeast(0)
