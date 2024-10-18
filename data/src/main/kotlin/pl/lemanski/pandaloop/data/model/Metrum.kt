package pl.lemanski.pandaloop.data.model

class Metrum(
    beats: Int,
    noteValue: Int
) {
    val beats: Int = beats
        get() = field.coerceIn(0, 32)
    val nodeValue: Int = noteValue
        get() = field.coerceIn(0, 32)
}