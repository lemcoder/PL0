package pl.lemanski.pandaloop.domain.di

sealed interface DependencyProvider<T> {
    fun provide(): T
}

class SingletonDependencyProvider<T>(
    private val impl: T
) : DependencyProvider<T> {

    override fun provide(): T {
        return impl
    }
}

class FactoryDependencyProvider<T>(
    private val impl: () -> T
) : DependencyProvider<T> {
    override fun provide(): T = impl()
}