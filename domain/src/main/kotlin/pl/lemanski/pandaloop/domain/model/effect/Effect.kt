package pl.lemanski.pandaloop.domain.model.effect

sealed interface Effect {
    class LowPassFilter(val frequency: Double) : Effect
    class Reverb(val decay: Double) : Effect
}