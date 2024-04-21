package pl.lemanski.pandaloop.domain.viewModel.looper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.lemanski.pandaloop.domain.PermissionManager

class LooperViewModel(
    private val permissionManager: PermissionManager
) : ViewModel(), LooperContract.ViewModel {
    private val _stateFlow = MutableStateFlow(
        LooperContract.State(
            text = "zero",
            onButtonClick = ::onButtonClick
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

    override fun onButtonClick(): Job = viewModelScope.launch {
        _stateFlow.update { state ->
            state.copy(
                text = state.text + "|_|"
            )
        }
    }
}