package pl.lemanski.pandaloop.domain.platform.system

interface SystemTimeProvider {
    fun getSystemMillis(): Long
}