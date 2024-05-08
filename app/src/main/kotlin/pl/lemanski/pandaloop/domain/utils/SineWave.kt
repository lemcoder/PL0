package pl.lemanski.pandaloop.domain.utils

import kotlin.math.PI
import kotlin.math.sin

fun generateSineWave(frequency: Float, durationSeconds: Double, sampleRate: Int): FloatArray {
    val numSamples = (durationSeconds * sampleRate).toInt()
    val wave = FloatArray(numSamples)

    val amplitude = .5f // Max amplitude for Float32 format
    val twopi = 2 * PI
    var phase = 0.0

    for (i in 0 until numSamples) {
        phase += frequency * twopi / sampleRate;
        if (phase > twopi) phase -= twopi;  // wrap
        wave[i] = amplitude * sin(phase).toFloat();
    }

    return wave
}
