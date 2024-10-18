package pl.lemanski.pandaloop.data.model

class Tempo(value: Int) {
    val value: Int = value
        get() = field.coerceIn(0, Int.MAX_VALUE)
}