package pl.lemanski.pandaloop.domain.viewModel.recording

import kotlinx.coroutines.Job
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.viewModel.PandaLoopViewModel

interface RecordingContract {
    interface ViewModel : PandaLoopViewModel<State> {
        fun onRecordClick(): Job
        fun onCountdownChange(count: Int)
    }

    data class State(
        val recordButton: IconButton,
        val countdownSelect: SelectInput
    ) {
        data class SelectInput(
            val label: String,
            val values: List<Option>,
            val onSelected: (Int) -> Unit,
            val selected: Int
        ) {
            data class Option(
                val value: Int,
                val label: String,
            )
        }

        data class IconButton(
            val icon: IconResource,
            val onClick: () -> Unit
        )
    }
}