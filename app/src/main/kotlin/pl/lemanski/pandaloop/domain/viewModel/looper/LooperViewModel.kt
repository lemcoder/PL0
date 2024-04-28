package pl.lemanski.pandaloop.domain.viewModel.looper

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.lemanski.pandaloop.Common
import pl.lemanski.pandaloop.Loop
import pl.lemanski.pandaloop.Recording
import pl.lemanski.pandaloop.TimeSignature
import pl.lemanski.pandaloop.domain.PermissionManager
import pl.lemanski.pandaloop.domain.model.TrackNumber
import pl.lemanski.pandaloop.domain.model.visual.IconResource

class LooperViewModel(
    private val permissionManager: PermissionManager,
    private val loop: Loop = Loop()
) : ViewModel(), LooperContract.ViewModel {
    private var mode: Mode = Mode.EDIT

    private val timeSignature: TimeSignature = Common
    private val tempo: Int = 60
    private val emptyBuffer = ByteArray(7056000) // FIXME

    private val tracks: MutableMap<TrackNumber, ByteArray> = mutableMapOf(
        0 to emptyBuffer,
        1 to emptyBuffer,
        2 to emptyBuffer,
        3 to emptyBuffer,
    )

    private val _stateFlow = MutableStateFlow(
        LooperContract.State(
            timeSignature = timeSignature.toString(),
            tempo = tempo.toString(),
            playbackButton = LooperContract.State.IconButton(
                icon = IconResource.PLAY_ARROW,
                onClick = ::onPlaybackClick
            ),
            tracks = tracks.toTrackCards()
        )
    )

    init {
        viewModelScope.launch {
            var recordAudioPermissionState: PermissionManager.PermissionState = PermissionManager.PermissionState.NOT_DETERMINED

            while (recordAudioPermissionState != PermissionManager.PermissionState.GRANTED) {
                recordAudioPermissionState = permissionManager.checkPermissionState(PermissionManager.Permission.RECORD_AUDIO)
                permissionManager.askPermission(PermissionManager.Permission.RECORD_AUDIO)
            }
        }
    }

    override val stateFlow: StateFlow<LooperContract.State> = _stateFlow.asStateFlow()

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

    override fun onTrackRecordClick(trackNumber: TrackNumber) = viewModelScope.launch {
        if (mode != Mode.EDIT) return@launch

        loop.setTrack(trackNumber)

        val recordingTime = timeSignature.getTimeWithTempo(tempo)
        val newRecording = Recording(recordingTime)
        newRecording.start()
        delay(recordingTime.toLong())
        newRecording.stop()

        val buffer = newRecording.recordedBuffer

        Log.d(this::class.simpleName, buffer.size.toString())
        tracks[trackNumber] = buffer
        mixTracks()

        _stateFlow.update { state ->
            state.copy(
                tracks = tracks.toTrackCards()
            )
        }
    }

    private fun mixTracks() {
        if (mode != Mode.EDIT) return

        tracks.forEach { (number, buffer) ->
            loop.setTrack(number)
            if (!buffer.contentEquals(emptyBuffer)) {
                loop.mixBuffer(buffer)
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

    internal enum class Mode {
        PLAYBACK,
        EDIT
    }
}