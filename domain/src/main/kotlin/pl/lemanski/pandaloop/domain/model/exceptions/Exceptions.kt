package pl.lemanski.pandaloop.domain.model.exceptions

class DependencyResolutionException : Exception()

class InvalidStateException(override val message: String?): Exception()

class NavigationStateException(override val message: String?) : Exception()