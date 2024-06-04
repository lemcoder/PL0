package pl.lemanski.pandaloop.domain.viewModel.looper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.model.track.Track
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.platform.log.Logger
import pl.lemanski.pandaloop.domain.repository.loop.LoopContextStateHolder
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.goTo
import pl.lemanski.pandaloop.domain.utils.Debounce

class LooperViewModel(
    private val navigationService: NavigationService,
    private val key: Destination.LoopScreen,
) : ViewModel(), LooperContract.ViewModel {
    private val loopContext = key.loopContext
    private val logger = Logger.get(this::class)
    private val debouncedOnRecordButtonClick = Debounce(::onRecordClick)
    private val _stateFlow = MutableStateFlow(
        LooperContract.State(
            playbackButton = Component.IconButton(
                icon = IconResource.PLAY_ARROW,
                onClick = ::onPlaybackClick
            ),
            recordButton = Component.IconButton(
                icon = IconResource.PLUS_SIGN,
                onClick = { debouncedOnRecordButtonClick(1_000) }
            ),
            tracks = listOf(),
        )
    )
    override val stateFlow: StateFlow<LooperContract.State> = _stateFlow.asStateFlow()

    fun initialize() {
        logger.i { "Initialize" }
        _stateFlow.update { state ->
            state.copy(
                tracks = loopContext.tracks.toTrackCards(),
            )
        }
    }

    override fun onPlaybackClick(): Job = viewModelScope.launch {
        logger.i { "Playback clicked" }

        if (loopContext.currentState.isPlaying()) {
            pausePlayback()
        } else {
            startPlayback()
        }
    }

    override fun onTrackRemoveClick(trackNumber: Int) = viewModelScope.launch {
        if (loopContext.currentState.isPlaying()) {
            pausePlayback()
        }

        loopContext.setTrack(trackNumber, null)

        _stateFlow.update { state ->
            state.copy(
                tracks = loopContext.tracks.toTrackCards()
            )
        }
    }

    override fun onRecordClick() {
        navigationService.goTo(
            destination = Destination.RecordingScreen(
                loopContext = loopContext,
                trackNumber = loopContext.tracks.size
            )
        )
    }

    override fun onCleared() {
        logger.i { "cleared" }
        super.onCleared()
    }

    private fun Map<Int, Track>.toTrackCards(): List<LooperContract.State.TrackCard> = map { (trackNumber, track) ->
        LooperContract.State.TrackCard(
            id = trackNumber,
            name = track.name,
            onRemoveClick = ::onTrackRemoveClick,
        )
    }

    private fun pausePlayback() {
        loopContext.pause()
        _stateFlow.update { state ->
            state.copy(
                playbackButton = state.playbackButton.copy(
                    icon = IconResource.PLAY_ARROW
                )
            )
        }
    }

    private fun startPlayback() {
        loopContext.playback()
        _stateFlow.update { state ->
            state.copy(
                playbackButton = state.playbackButton.copy(
                    icon = IconResource.PAUSE_BARS
                )
            )
        }
    }

    private fun LoopContextStateHolder.State.isPlaying() = this == LoopContextStateHolder.State.PLAY
}