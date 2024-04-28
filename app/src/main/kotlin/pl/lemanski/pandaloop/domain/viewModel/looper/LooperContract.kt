package pl.lemanski.pandaloop.domain.viewModel.looper

import kotlinx.coroutines.Job
import pl.lemanski.pandaloop.domain.model.TrackNumber
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface LooperContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun onPlaybackClick()
        fun onTrackRemoveClick(trackNumber: TrackNumber)
        fun onTrackRecordClick(trackNumber: TrackNumber) : Job
    }

    data class State(
        val timeSignature: String,
        val tempo: String,
        val tracks: List<TrackCard>,
        val playbackButton: IconButton
    ) {
        data class TrackCard(
            val id: Int,
            val name: String,
            val isEmpty: Boolean,
            val timestamp: Long,
            val onRemoveClick: (Int) -> Unit,
            val onRecordClick: (Int) -> Unit,
        )

        data class IconButton(
            val icon: IconResource,
            val onClick: () -> Unit
        )
    }
}