package pl.lemanski.pandaloop.domain.di

interface DependencyProvider<T> {
    fun provide(): T
}

internal class SingletonDependencyProvider<T>(
    private val impl: T
) : DependencyProvider<T> {

    override fun provide(): T {
        return impl
    }
}