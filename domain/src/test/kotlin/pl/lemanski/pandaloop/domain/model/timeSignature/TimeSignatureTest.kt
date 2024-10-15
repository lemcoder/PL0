package pl.lemanski.pandaloop.domain.model.timeSignature

import pl.lemanski.mikroaudio.AudioEngine
import pl.lemanski.mikroaudio.MikroAudio
import pl.lemanski.pandaloop.domain.utils.TestAudioEngine
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeSignatureTest {
    @Test
    fun shouldCalculateCorrectAmountOfMilliseconds() {
        assertEquals(2666.6666666666665, TimeSignature.COMMON.getTimeWithTempo(90))
        assertEquals(4_000.0, TimeSignature.COMMON.getTimeWithTempo(60))
        assertEquals(3_000.0, TimeSignature.THREE_FOURS.getTimeWithTempo(60))
    }

    @Test
    fun shouldGetCorrectBufferSize() {
        val testOptions = object : AudioEngine.Options {
            override val channelCount: Int = 1
            override val sampleRate: Int = 44_100
        }
        val mikroAudio = MikroAudio(audioEngine = TestAudioEngine())
        var bufferSize = mikroAudio.getBufferSizeInBytesWithTempo(TimeSignature.COMMON, 90)
        assertEquals(470_804.0, bufferSize.toDouble(), 500.0) // FIXME
        bufferSize = mikroAudio.getBufferSizeInBytesWithTempo(TimeSignature.COMMON, 60)
        assertEquals(705_600.0, bufferSize.toDouble(), 500.0) // FIXME
    }
}