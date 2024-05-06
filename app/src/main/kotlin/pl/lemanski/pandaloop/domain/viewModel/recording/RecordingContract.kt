package pl.lemanski.pandaloop.domain.viewModel.recording

import kotlinx.coroutines.Job
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface RecordingContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun onRecordClick(): Job
        fun onCountdownChanged(count: Int)
    }

    data class State(
        val recordButton: Component.IconButton,
        val countdownSelect: Component.TextSelect,
        val countdownScrim: CountdownScrim
    ) {
        data class CountdownScrim(
            val visible: Boolean,
            val currentCount: Int,
            val countdown: Int,
            val isRecording: Boolean
        )
    }
}