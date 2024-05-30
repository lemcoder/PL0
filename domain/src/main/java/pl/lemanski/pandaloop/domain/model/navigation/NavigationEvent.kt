package pl.lemanski.pandaloop.domain.model.navigation

data class NavigationEvent(
    val destination: Destination,
    val direction: Direction
) {
    enum class Direction {
        FORWARD, BACKWARD
    }
}