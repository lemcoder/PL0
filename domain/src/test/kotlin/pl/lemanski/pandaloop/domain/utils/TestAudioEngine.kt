package pl.lemanski.pandaloop.domain.utils

import pl.lemanski.mikroaudio.AudioEngine

class TestAudioEngine : AudioEngine {
    override val options: AudioEngine.Options
        get() = object : AudioEngine.Options {
            override val channelCount: Int = 1
            override val sampleRate: Int = 44_100
        }

    override fun setupPlayback(buffer: ByteArray) {
    }

    override fun setupRecording(bufferSize: Long) {
    }

    override fun startPlayback() {
    }

    override fun startRecording() {
    }

    override fun stopPlayback() {
    }

    override fun stopRecording(): ByteArray {
        return byteArrayOf()
    }
}