package pl.lemanski.pandaloop.domain.platform

interface SystemTimeProvider {
    fun getSystemMillis(): Long
}