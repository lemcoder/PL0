package pl.lemanski.pandaloop.domain.service.navigation

import kotlinx.coroutines.flow.StateFlow
import pl.lemanski.pandaloop.domain.model.exceptions.NavigationStateException
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.model.navigation.NavigationEvent

abstract class NavigationService {
    abstract val navStack: ArrayDeque<Destination>
    abstract val navigationState: StateFlow<NavigationEvent>

    internal abstract fun navigate(reducer: (NavigationEvent) -> NavigationEvent)
}

fun NavigationService.goTo(destination: Destination) {
    navStack.addLast(destination)

    navigate {
        NavigationEvent(
            destination = navStack.last(),
            direction = NavigationEvent.Direction.FORWARD
        )
    }
}

fun NavigationService.back(): Unit? {
    if (navStack.size <= 1) {
        return null
    }

    navStack.removeLast()

    return navigate {
        NavigationEvent(
            destination = navStack.last(),
            direction = NavigationEvent.Direction.BACKWARD
        )
    }
}

inline fun <reified T : Destination> NavigationService.key(): T? {
    val entry: List<T> = navStack.filterIsInstance<T>()
    if (entry.size > 1) throw NavigationStateException("More than one key of the same type on the stack: \n ${navStack.map { "- $it\n" }}")
    return entry.firstOrNull()
}