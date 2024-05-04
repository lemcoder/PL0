package pl.lemanski.pandaloop.domain.navigation

import android.graphics.Path.Direction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.lemanski.pandaloop.domain.exceptions.NavigationStateException

class NavigationController {
    private val stack: ArrayDeque<Destination> = ArrayDeque(listOf(Destination.StartScreen))
    private val _navigationState: MutableStateFlow<NavigationEvent> = MutableStateFlow(
        NavigationEvent(
            destination = stack.last(),
            direction = NavigationEvent.Direction.FORWARD
        )
    )
    val navigationState: StateFlow<NavigationEvent> = _navigationState.asStateFlow()

    fun goTo(destination: Destination) {
        stack.addLast(destination)
        _navigationState.update {
            NavigationEvent(
                destination = stack.last(),
                direction = NavigationEvent.Direction.FORWARD
            )
        }
    }

    fun goBack() {
        stack.removeLast()
        _navigationState.update {
            NavigationEvent(
                destination = stack.last(),
                direction = NavigationEvent.Direction.BACKWARD
            )
        }
    }

    internal inline fun <reified T : Destination> keyOfType(): T? {
        val entry: List<T> = stack.mapNotNull { it as? T }
        if (entry.size != 1) throw NavigationStateException()
        return entry.firstOrNull()
    }
}

data class NavigationEvent(
    val destination: Destination,
    val direction: Direction
) {
    enum class Direction {
        FORWARD, BACKWARD
    }
}