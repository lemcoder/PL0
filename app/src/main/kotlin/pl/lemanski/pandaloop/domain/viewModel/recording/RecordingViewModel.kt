package pl.lemanski.pandaloop.domain.viewModel.recording

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.lemanski.pandaloop.domain.model.visual.IconResource
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController

class RecordingViewModel(
    private val navigationController: NavigationController,
    private val key: Destination.RecordingScreen
) : RecordingContract.ViewModel, ViewModel() {
    private val _stateFlow = MutableStateFlow(
        RecordingContract.State(
            recordButton = RecordingContract.State.IconButton(
                icon = IconResource.REC_DOT,
                onClick = ::onRecordClick
            ),
            countdownSelect = RecordingContract.State.SelectInput(
                label = "TODO label",
                values = listOf(
                    RecordingContract.State.SelectInput.Option(
                        label = "1 bar",
                        value = 1
                    ),
                    RecordingContract.State.SelectInput.Option(
                        label = "2 bars",
                        value = 2
                    ),
                    RecordingContract.State.SelectInput.Option(
                        label = "3 bars",
                        value = 3
                    ),
                    RecordingContract.State.SelectInput.Option(
                        label = "4 bars",
                        value = 4
                    )
                ),
                onSelected = ::onCountdownChange,
                selected = 1
            )
        )
    )

    override val stateFlow: StateFlow<RecordingContract.State> = _stateFlow.asStateFlow()

    override fun onRecordClick(): Job {
        TODO("Not yet implemented")
    }

    override fun onCountdownChange(count: Int) {
        TODO("Not yet implemented")
    }
}