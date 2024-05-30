package pl.lemanski.pandaloop.domain.repository.loop

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import pl.lemanski.pandaloop.core.Loop
import pl.lemanski.pandaloop.core.Recording
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.core.internal.Closeable
import pl.lemanski.pandaloop.domain.model.exceptions.InvalidStateException
import pl.lemanski.pandaloop.domain.model.track.Track
import pl.lemanski.pandaloop.domain.platform.log.Logger
import pl.lemanski.pandaloop.domain.utils.emptyBuffer
import pl.lemanski.pandaloop.domain.utils.getBufferSizeInBytes
import pl.lemanski.pandaloop.domain.utils.getTime
import pl.lemanski.pandaloop.dsp.Mixer
import pl.lemanski.pandaloop.dsp.utils.toByteArray
import pl.lemanski.pandaloop.dsp.utils.toFloatArray
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LoopContext(
    val timeSignature: TimeSignature,
    val tempo: Int,
    val measures: Int,
    private val mixer: Mixer = Mixer(),
    private val loopContextStateHolder: LoopContextStateHolder = LoopContextStateHolder()
) : Closeable {
    private val logger = Logger.get(this::class)
    private val loopScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val loop = Loop()
    private val _tracks: MutableMap<Int, Track> = mutableMapOf()

    private var playbackBuffer = timeSignature.emptyBuffer(tempo, measures)

    init {
        logger.i { "Loop context created with params: timeSignature: ${timeSignature.name}, tempo: $tempo" }
    }

    val currentState
        get() = loopContextStateHolder.state
    val tracks
        get() = _tracks.toMap()

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun record(): Track = suspendCancellableCoroutine { continuation ->
        logger.i { "Recording called" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.REC)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }

        val recordingJob = loopScope.launch {
            val recording = Recording(timeSignature.getBufferSizeInBytes(tempo, measures))
            val recPaddingMillis = 200L

            try {
                recording.start()
                logger.d { "Recording..." }
                delay(timeSignature.getTime(tempo, measures) + recPaddingMillis)
                recording.stop()
                logger.d { "Recording Finished" }
            } catch (ex: Exception) {
                logger.e { "Recording failed with exception: ${ex.message}" }
                continuation.resumeWithException(ex)
            }

            loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)

            continuation.resume(
                value = Track(name = "Track ${_tracks.size + 1}", data = recording.recordedBuffer, effects = listOf()),
                onCancellation = {
                    logger.d { "Recording cancelled" }
                    loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
                    logger.d { "Loop context state: ${loopContextStateHolder.state}" }
                }
            )
        }

        continuation.invokeOnCancellation {
            recordingJob.cancel()
            logger.d { "Recording cancelled" }
            loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
            logger.d { "Loop context state: ${loopContextStateHolder.state}" }
        }
    }

    suspend fun playback() = suspendCancellableCoroutine { continuation ->
        logger.i { "Playback called" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.PLAY)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }

        val playbackJob = loopScope.launch {
            try {
                loop.setBuffer(playbackBuffer)
                loop.play()
                continuation.resume(Unit)
            } catch (ex: Exception) {
                logger.e { "Playback failed with exception: ${ex.message}" }
            }
        }

        continuation.invokeOnCancellation {
            playbackJob.cancel()
            logger.d { "Playback cancelled" }
            loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
            logger.d { "Loop context state: ${loopContextStateHolder.state}" }
        }
    }

    fun stop() {
        logger.i { "Stop called" }
        if (loopContextStateHolder.state != LoopContextStateHolder.State.PLAY) {
            throw InvalidStateException("Cannot stop playback when not playing")
        }

        loop.stop()

        logger.d { "Playback stopped" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }
    }

    suspend fun setTrack(id: Int, track: Track?, volume: Float = 1.0f) {
        if (track == null) {
            _tracks.remove(id)
            _tracks.values.forEach { mixTrack(it, volume) } // Mix all remaining tracks when removing
            return
        }

        _tracks[id] = track
        mixTrack(track, volume) // Mix only new track when adding
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun mixTrack(track: Track, volume: Float) = suspendCancellableCoroutine<Unit> { continuation ->
        logger.i { "setTrack called with params: timeSignature: ${timeSignature.name}, tempo: $tempo" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.MIX)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }

        val mixingJob = loopScope.launch {
            try {
                mixer.mixPcmFramesF32(track.data.toFloatArray(), playbackBuffer.toFloatArray(), volume).toByteArray()
                loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
                continuation.resume(
                    value = Unit,
                    onCancellation = {
                        logger.d { "Mixing cancelled" }
                        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
                        logger.d { "Loop context state: ${loopContextStateHolder.state}" }
                    }
                )
            } catch (ex: Exception) {
                logger.e { "Mixing failed with exception: ${ex.message}" }
                continuation.resumeWithException(ex)
            }
        }

        continuation.invokeOnCancellation {
            logger.d { "Mixing cancelled" }
            mixingJob.cancel()
            logger.d { "Loop context state: ${loopContextStateHolder.state}" }
        }
    }

    override fun close() {
        loopScope.coroutineContext.cancelChildren()
        loopScope.cancel()
    }
}