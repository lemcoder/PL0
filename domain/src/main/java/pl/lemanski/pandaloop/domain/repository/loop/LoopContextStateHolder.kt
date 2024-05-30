package pl.lemanski.pandaloop.domain.repository.loop

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import pl.lemanski.pandaloop.domain.model.exceptions.InvalidStateException

class LoopContextStateHolder {
    private val _state = atomic(State.IDLE)
    val state: State by _state

    fun tryUpdateState(newState: State) {
        val current = state
        val expected = when (current) {
            State.REC  -> State.IDLE
            State.PLAY -> State.IDLE
            State.MIX  -> State.IDLE
            State.IDLE -> {
                _state.update { newState }
                return
            }
        }

        if (newState != expected) {
            throw InvalidStateException("Invalid state transition from $current to $newState")
        }

        _state.lazySet(newState)
    }

    enum class State {
        IDLE,
        REC,
        PLAY,
        MIX
    }
}