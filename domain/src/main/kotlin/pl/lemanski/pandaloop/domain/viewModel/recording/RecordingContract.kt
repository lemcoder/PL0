package pl.lemanski.pandaloop.domain.viewModel.recording

import kotlinx.coroutines.Job
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface RecordingContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun record(): Job
    }

    data class State(
        val countdown: String?,
        val metronome: Metronome?
    ) {
        data class Metronome(
            val periodMillis: Long,
        )
    }
}