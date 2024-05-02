package pl.lemanski.pandaloop.domain.viewModel.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.lemanski.pandaloop.domain.platform.PermissionManager

class StartViewModel(
    val permissionManager: PermissionManager
) : StartContract.ViewModel, ViewModel() {
    private val _stateFlow = MutableStateFlow(
        StartContract.State(
            tempoPicker = StartContract.State.TempoPicker,
            timeSignatureSelect = StartContract.State.TimeSignatureSelect
        )
    )

    override val stateFlow: StateFlow<StartContract.State> = _stateFlow.asStateFlow()

    override fun onTempoChanged(tempo: Int) {
        TODO("Not yet implemented")
    }

    override fun onTimeSignatureChanged(timeSignature: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateLoopClicked() {
        TODO("Not yet implemented")
    }


}