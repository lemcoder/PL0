package pl.lemanski.pandaloop.domain.viewModel.looper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import pl.lemanski.pandaloop.core.Loop
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.utils.emptyBuffer
import pl.lemanski.pandaloop.dsp.LowPassFilter
import pl.lemanski.pandaloop.dsp.Mixer
import pl.lemanski.pandaloop.dsp.utils.toByteArray
import pl.lemanski.pandaloop.dsp.utils.toFloatArray

class LooperViewModel(
    private val navigationController: NavigationController,
    private val key: Destination.LoopScreen,
    private val loop: Loop = Loop()
) : ViewModel(), LooperContract.ViewModel {
    private val timeSignature: TimeSignature = key.timeSignature
    private val tempo: Int = key.tempo
    private val measures: Int = key.measures
    private val tracks: MutableMap<Int, ByteArray> = key.tracks
    private val emptyBuffer: ByteArray = timeSignature.emptyBuffer(tempo, measures)
    private val busyLock: Mutex = Mutex(false)

    private val _stateFlow = MutableStateFlow(
        LooperContract.State(
            timeSignature = timeSignature.toString(),
            tempo = tempo.toString(),
            playbackButton = Component.IconButton(
                icon = IconResource.PLAY_ARROW,
                onClick = ::onPlaybackClick
            ),
            tracks = tracks.toTrackCards()
        )
    )

    override val stateFlow: StateFlow<LooperContract.State> = _stateFlow.asStateFlow()

    override fun initialize() {
        mixTracks()
    }

    override fun onPlaybackClick() {
        val isPlayback = _stateFlow.value.playbackButton.icon == IconResource.PAUSE_BARS // TODO better check

        if (isPlayback) {
            loop.stop()
            busyLock.unlock()
            _stateFlow.update { state ->
                state.copy(
                    playbackButton = state.playbackButton.copy(icon = IconResource.PLAY_ARROW)
                )
            }

        } else {
            busyLock.tryLock()
            loop.play()
            _stateFlow.update { state ->
                state.copy(
                    playbackButton = state.playbackButton.copy(icon = IconResource.PAUSE_BARS)
                )
            }
        }
    }

    override fun onTrackRemoveClick(trackNumber: Int) {
        if (busyLock.isLocked) return

        tracks[trackNumber] = emptyBuffer
        mixTracks()

        _stateFlow.update { state ->
            state.copy(
                tracks = tracks.toTrackCards()
            )
        }
    }

    override fun onTrackRecordClick(trackNumber: Int) {
        if (busyLock.isLocked) return

        navigationController.goTo(
            Destination.RecordingScreen(
                trackNumber = trackNumber,
                tempo = tempo,
                timeSignature = timeSignature,
                measures = measures
            )
        )
    }

    private fun mixTracks() {
        if (busyLock.isLocked) return

        viewModelScope.launch {
            busyLock.lock()
            withContext(Dispatchers.Default) {
                var outputBuffer = FloatArray((emptyBuffer.size.toFloat() / 4f).toInt().coerceAtLeast(0))
                tracks.forEach { (number, buffer) ->
                    outputBuffer = Mixer().mixPcmFramesF32(buffer.toFloatArray(), outputBuffer, 1f)
                }

                loop.setBuffer(buffer = outputBuffer.toByteArray())

                _stateFlow.update { state ->
                    state.copy(
                        tracks = tracks.toTrackCards()
                    )
                }
            }
            busyLock.unlock()
        }
    }

    override fun onEffectsRackClick(trackNumber: Int) {
        tracks[trackNumber] = LowPassFilter(8_000, tracks[trackNumber]!!.toFloatArray()).apply().toByteArray()
    }

    private fun MutableMap<Int, ByteArray>.toTrackCards(): List<LooperContract.State.TrackCard> {
        return this.map { (number, buffer) ->
            LooperContract.State.TrackCard(
                id = number,
                name = "${number + 1}",
                isEmpty = buffer.contentEquals(emptyBuffer),
                onRemoveClick = ::onTrackRemoveClick,
                onRecordClick = ::onTrackRecordClick,
                onEffectClick = ::onEffectsRackClick
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        loop.close()
    }
}