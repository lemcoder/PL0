package pl.lemanski.pandaloop.domain.utils

class Debounce(private val block: () -> Unit) {
    private var lastTime = 0L

    operator fun invoke(millis: Long) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > millis) {
            lastTime = currentTime
            block()
        }
    }
}