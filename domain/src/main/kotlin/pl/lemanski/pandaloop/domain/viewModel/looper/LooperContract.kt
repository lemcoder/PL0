package pl.lemanski.pandaloop.domain.viewModel.looper

import kotlinx.coroutines.Job
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface LooperContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun onPlaybackClick(): Job
        fun onTrackRemoveClick(trackNumber: Int): Job
        fun onRecordClick()
    }

    data class State(
        val tracks: List<TrackCard>,
        val playbackButton: Component.IconButton?,
        val recordButton: Component.IconButton?
    ) {
        data class TrackCard(
            val id: Int,
            val name: String,
            val onRemoveClick: (Int) -> Unit,
        )
    }
}