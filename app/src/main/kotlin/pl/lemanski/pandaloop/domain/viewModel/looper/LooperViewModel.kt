package pl.lemanski.pandaloop.domain.viewModel.looper

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.lemanski.pandaloop.Loop
import pl.lemanski.pandaloop.TimeSignature
import pl.lemanski.pandaloop.domain.model.TrackNumber
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.utils.emptyBuffer

class LooperViewModel(
    private val navigationController: NavigationController,
    private val key: Destination.LoopScreen,
    private val loop: Loop = Loop()
) : ViewModel(), LooperContract.ViewModel {
    private var mode: Mode = Mode.EDIT
    private val timeSignature: TimeSignature = key.timeSignature
    private val tempo: Int = key.tempo
    private val tracks: MutableMap<TrackNumber, ByteArray> = key.tracks
    private val emptyBuffer: ByteArray = timeSignature.emptyBuffer(tempo)

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
        if (mode == Mode.PLAYBACK) {
            loop.stop()
            mode = Mode.EDIT
            _stateFlow.update { state ->
                state.copy(
                    playbackButton = state.playbackButton.copy(icon = IconResource.PLAY_ARROW)
                )
            }

        } else {
            Log.e("LooperViewModel", "${tracks.map { it.value.size.toString() }}")
            loop.play()
            mode = Mode.PLAYBACK
            _stateFlow.update { state ->
                state.copy(
                    playbackButton = state.playbackButton.copy(icon = IconResource.PAUSE_BARS)
                )
            }
        }
    }

    override fun onTrackRemoveClick(trackNumber: TrackNumber) {
        if (mode != Mode.EDIT) return

        tracks[trackNumber] = emptyBuffer
        mixTracks()

        _stateFlow.update { state ->
            state.copy(
                tracks = tracks.toTrackCards()
            )
        }
    }

    override fun onTrackRecordClick(trackNumber: TrackNumber) {
        if (mode != Mode.EDIT) return

        navigationController.goTo(
            Destination.RecordingScreen(
                trackNumber = trackNumber,
                tempo = tempo,
                timeSignature = timeSignature
            )
        )
    }

    private fun mixTracks() {
        if (mode != Mode.EDIT) return

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tracks.forEach { (number, buffer) ->
                    loop.setTrack(number)
                    loop.mixBuffer(buffer)
                }

                _stateFlow.update { state ->
                    state.copy(
                        tracks = tracks.toTrackCards()
                    )
                }

            }
        }
    }

    private fun MutableMap<Int, ByteArray>.toTrackCards(): List<LooperContract.State.TrackCard> {
        return this.map { (number, buffer) ->
            LooperContract.State.TrackCard(
                id = number,
                name = "${number + 1}",
                timestamp = 0, // TODO
                isEmpty = buffer.contentEquals(emptyBuffer),
                onRemoveClick = ::onTrackRemoveClick,
                onRecordClick = ::onTrackRecordClick,
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        loop.close()
    }

    internal enum class Mode {
        PLAYBACK,
        EDIT
    }
}