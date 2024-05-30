package pl.lemanski.pandaloop.domain.service.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.model.navigation.NavigationEvent
import pl.lemanski.pandaloop.domain.platform.log.Logger

class NavigationServiceImpl : NavigationService() {
    private val logger: Logger = Logger.get(this::class)
    override val navStack: ArrayDeque<Destination> = ArrayDeque(listOf(Destination.StartScreen))
    private val _navigationState: MutableStateFlow<NavigationEvent> = MutableStateFlow(
        NavigationEvent(
            destination = navStack.last(),
            direction = NavigationEvent.Direction.FORWARD
        )
    )
    override val navigationState: StateFlow<NavigationEvent> = _navigationState.asStateFlow()

    override fun navigate(reducer: (NavigationEvent) -> NavigationEvent) {
        logger.i { "Navigation event received!" }
        _navigationState.update(reducer)
    }
}