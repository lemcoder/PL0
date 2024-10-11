package pl.lemanski.pandaloop.domain.repository.loop

import io.mockk.mockk
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import pl.lemanski.mikroaudio.AudioEngine
import pl.lemanski.mikroaudio.MikroAudio
import pl.lemanski.pandaloop.domain.model.exceptions.InvalidStateException
import pl.lemanski.pandaloop.domain.model.timeSignature.TimeSignature
import pl.lemanski.pandaloop.domain.model.track.Track

class LoopContextTest {

    private val mockEngine: AudioEngine = mockk(relaxed = true)

    private fun TestScope.loopContext() = LoopContext(
        measures = 1,
        timeSignature = TimeSignature.COMMON,
        tempo = 60,
        loopCoordinator = MikroAudio(mockEngine),
    )

    @Test
    fun should_update_state_to_REC_and_back_to_IDLE_on_completion() = runTest {
        val loopContext = loopContext()

        loopContext.record()

        assertEquals(LoopContextStateHolder.State.IDLE, loopContext.currentState)
    }

    @Test
    fun should_cancel_recording_job_on_cancellation() = runTest {
        val loopContext = loopContext()

        launch { loopContext.record() }.cancel()

        assertEquals(LoopContextStateHolder.State.IDLE, loopContext.currentState)
    }

    @Test
    fun stop_should_change_state_to_IDLE() = runTest {
        val loopContext = loopContext()
        loopContext.playback()
        assertEquals(LoopContextStateHolder.State.PLAY, loopContext.currentState)
        loopContext.pause()
        assertEquals(LoopContextStateHolder.State.IDLE, loopContext.currentState)
    }

    @Test
    fun stop_should_throw_InvalidStateException_if_state_is_not_PLAY() = runTest {
        val loopContext = loopContext()
        assertThrows(InvalidStateException::class.java) { loopContext.pause() }
    }

    @Test
    fun should_update_state_to_MIX_and_back_to_IDLE_on_completion() = runTest {
        val loopContext = loopContext()
        loopContext.setTrack(0, null)
        assertEquals(LoopContextStateHolder.State.IDLE, loopContext.currentState)
    }

    @Test
    fun should_cancels_mixing_job_on_cancellation() = runTest {
        val loopContext = loopContext()
        val mixingJob = launch { loopContext.setTrack(0, Track("Track 1", byteArrayOf(), listOf())) }
        mixingJob.cancel()
        assertEquals(LoopContextStateHolder.State.IDLE, loopContext.currentState)
    }
}