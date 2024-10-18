package pl.lemanski.pandaloop.domain.repository.loop

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import pl.lemanski.mikroaudio.MikroAudio
import pl.lemanski.pandaloop.domain.dsp.Mixer
import pl.lemanski.pandaloop.domain.model.exceptions.InvalidStateException
import pl.lemanski.pandaloop.domain.model.timeSignature.TimeSignature
import pl.lemanski.pandaloop.domain.model.timeSignature.emptyBuffer
import pl.lemanski.pandaloop.domain.model.timeSignature.getBufferSizeInBytes
import pl.lemanski.pandaloop.domain.model.timeSignature.getTime
import pl.lemanski.pandaloop.domain.model.track.Track
import pl.lemanski.pandaloop.domain.platform.log.Logger
import pl.lemanski.pandaloop.domain.utils.Closeable
import pl.lemanski.pandaloop.domain.utils.toByteArray
import pl.lemanski.pandaloop.domain.utils.toFloatArray
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class LoopContext(
    val timeSignature: TimeSignature,
    val tempo: Int,
    val measures: Int,
    val loopCoordinator: MikroAudio = MikroAudio()
) : Closeable {
    private val logger = Logger.get(this::class)
    private val loopScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val _tracks: MutableMap<Int, Track> = mutableMapOf()
    private var playbackBuffer = loopCoordinator.emptyBuffer(timeSignature, tempo, measures)
    private val mixer: Mixer = Mixer()
    private val loopContextStateHolder: LoopContextStateHolder = LoopContextStateHolder()

    init {
        logger.i { "Loop context created with params: timeSignature: ${timeSignature.name}, tempo: $tempo, measures: $measures" }
        // TODO use value classes
        check(measures >= 0)
        check(tempo >= 0)
    }

    val currentState
        get() = loopContextStateHolder.state
    val tracks
        get() = _tracks.toMap()

    suspend fun record(): Track = suspendCancellableCoroutine { continuation ->
        logger.i { "Recording called" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.REC)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }

        val recordingJob = loopScope.launch {
            val drift = 200L
            val recordBufferSize = loopCoordinator.getBufferSizeInBytes(timeSignature, tempo, measures)
            var recordedBuffer: ByteArray = byteArrayOf()

            try {
                loopCoordinator.record(recordBufferSize)
                logger.d { "Recording..." }
                delay(timeSignature.getTime(tempo, measures) + drift)
                recordedBuffer = loopCoordinator.stopRecording()
                logger.d { "Recording Finished" }
            } catch (ignore: CancellationException) {
            } catch (ex: Exception) {
                logger.e { "Recording failed with exception: ${ex.message}" }
                continuation.resumeWithException(ex)
            } finally {
                loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
                continuation.resume(Track(name = "Track ${_tracks.size + 1}", data = recordedBuffer, effects = listOf()))
            }
        }

        continuation.invokeOnCancellation {
            recordingJob.cancel()
            loopCoordinator.stopRecording()
            logger.i { "Recording cancelled" }
            loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
            logger.d { "Loop context state: ${loopContextStateHolder.state}" }
        }
    }

    fun playback() {
        logger.i { "Playback called" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.PLAY)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }

        try {
            loopCoordinator.playback(playbackBuffer)
        } catch (ex: Exception) {
            logger.e { "Playback failed with exception: ${ex.message}" }
        }
    }

    fun pause() {
        logger.i { "Stop called" }
        if (loopContextStateHolder.state != LoopContextStateHolder.State.PLAY) {
            throw InvalidStateException("Cannot stop playback when not playing")
        }

        loopCoordinator.stopPlayback()

        logger.d { "Playback stopped" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }
    }

    fun setTrack(id: Int, track: Track?, volume: Float = 1.0f) {
        if (currentState != LoopContextStateHolder.State.IDLE) {
            throw InvalidStateException("Cannot mix when playback or recording is active")
        }

        logger.i { "setTrack called with params: track: ${track?.name}, volume: $volume" }
        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.MIX)
        logger.d { "Loop context state: ${loopContextStateHolder.state}" }

        if (track == null) {
            _tracks.remove(id)
            playbackBuffer = loopCoordinator.emptyBuffer(timeSignature, tempo, measures)
            _tracks.values.forEach { mixTrack(it, volume) } // Mix all remaining tracks when removing
        } else {
            _tracks[id] = track
            mixTrack(track, volume) // Mix only new track when adding
        }

        loopContextStateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
    }

    private fun mixTrack(track: Track, volume: Float) {
        try {
            playbackBuffer = mixer.mixPcmFramesF32(track.data.toFloatArray(), playbackBuffer.toFloatArray(), volume).toByteArray()
        } catch (ex: Exception) {
            logger.e { "Mixing failed with exception: ${ex.message}" }
        }
    }

    override fun close() {
        loopCoordinator.stopPlayback()
        loopScope.coroutineContext.cancelChildren()
        loopScope.cancel()
    }
}