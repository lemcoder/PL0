package pl.lemanski.pandaloop.domain.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.lemanski.pandaloop.domain.exceptions.NavigationStateException

class NavigationController {
    private val stack: ArrayDeque<Destination> = ArrayDeque(listOf(Destination.StartScreen))
    private val _navigationState: MutableStateFlow<Destination> = MutableStateFlow(stack.last())
    val navigationState: StateFlow<Destination> = _navigationState.asStateFlow()

    fun goTo(destination: Destination) {
        stack.addLast(destination)
        _navigationState.update { stack.last() }
    }

    fun goBack() {
        stack.removeLast()
        _navigationState.update { stack.last() }
    }

    internal inline fun <reified T : Destination> keyOfType(): T? {
        val entry: List<T> = stack.mapNotNull { it as? T }
        if (entry.size != 1) throw NavigationStateException()
        return entry.firstOrNull()
    }
}