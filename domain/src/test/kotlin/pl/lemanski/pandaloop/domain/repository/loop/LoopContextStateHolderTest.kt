package pl.lemanski.pandaloop.domain.repository.loop

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import pl.lemanski.pandaloop.domain.model.exceptions.InvalidStateException
import kotlin.test.Test

class LoopContextStateHolderTest {

    @Test
    fun should_update_state_to_IDLE_when_current_state_is_REC_PLAY_or_MIX() = runTest {
        val stateHolder = LoopContextStateHolder()
        stateHolder.tryUpdateState(LoopContextStateHolder.State.REC)
        stateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        assertEquals(LoopContextStateHolder.State.IDLE, stateHolder.state)

        stateHolder.tryUpdateState(LoopContextStateHolder.State.PLAY)
        stateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        assertEquals(LoopContextStateHolder.State.IDLE, stateHolder.state)

        stateHolder.tryUpdateState(LoopContextStateHolder.State.MIX)
        stateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        assertEquals(LoopContextStateHolder.State.IDLE, stateHolder.state)
    }

    @Test
    fun should_allow_any_state_from_IDLE_state() = runTest {
        val stateHolder = LoopContextStateHolder()
        stateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        stateHolder.tryUpdateState(LoopContextStateHolder.State.REC)
        assertEquals(LoopContextStateHolder.State.REC, stateHolder.state)

        stateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        stateHolder.tryUpdateState(LoopContextStateHolder.State.PLAY)
        assertEquals(LoopContextStateHolder.State.PLAY, stateHolder.state)

        stateHolder.tryUpdateState(LoopContextStateHolder.State.IDLE)
        stateHolder.tryUpdateState(LoopContextStateHolder.State.MIX)
        assertEquals(LoopContextStateHolder.State.MIX, stateHolder.state)
    }

    @Test
    fun should_throw_InvalidStateException_if_new_state_is_not_expected() = runTest {
        val stateHolder = LoopContextStateHolder()
        stateHolder.tryUpdateState(LoopContextStateHolder.State.REC)
        assertThrows(InvalidStateException::class.java) {
            stateHolder.tryUpdateState(LoopContextStateHolder.State.MIX)
        }
    }
}