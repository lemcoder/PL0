package pl.lemanski.pandaloop.domain.dsp

class Mixer {
    fun mixPcmFramesF32(input: FloatArray, output: FloatArray, volume: Float): FloatArray {
        if (input.isEmpty() || output.isEmpty()) {
            // TODO custom exceptionss
            throw IllegalArgumentException("Invalid arguments")
        }

        if (input.size != output.size) {
            throw IllegalArgumentException("Input and output must be the same size (provided: in[${input.size}] and out[${output.size}])")
        }

        if (volume == 0f) {
            return output
        }

        if (volume == 1f) {
            for (i in output.indices) {
                output[i] += input[i]
            }
        } else {
            for (i in output.indices) {
                output[i] += (input[i] * volume)
            }
        }

        return output
    }
}