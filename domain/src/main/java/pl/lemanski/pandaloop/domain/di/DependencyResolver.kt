package pl.lemanski.pandaloop.domain.di

import pl.lemanski.pandaloop.domain.model.exceptions.DependencyResolutionException

object DependencyResolver {
    val providers: MutableList<DependencyProvider<*>> = mutableListOf()

    fun addProviders(block: () -> List<DependencyProvider<*>>) {
        providers.addAll(block())
    }

    @Throws(DependencyResolutionException::class)
    inline fun <reified T> resolve(): T {
        val provider = providers.find { it.provide() is T } ?: throw DependencyResolutionException()

        return provider.provide() as T
    }
}