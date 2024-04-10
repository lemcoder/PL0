package pl.lemanski.pandaloop

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
            SideEffect {
                launcher.launch(Manifest.permission.RECORD_AUDIO)
            }


            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var buffer by remember {
        mutableStateOf(byteArrayOf())
    }
    var trackNumber by remember {
        mutableIntStateOf(0)
    }
    Column {
        Button(onClick = {
            AudioRecorder.initializeRecording(44100)
            AudioRecorder.startRecording()
        }) {
            Text(text = "Start recording")
        }

        Button(onClick = {
            buffer = AudioRecorder.stopRecording()
            AudioRecorder.uninitializeRecording()
        }) {
            Text(text = "Stop recording")
        }

        Button(onClick = {
            if (buffer.isEmpty()) return@Button
            if (trackNumber == 0) {
                AudioPlayer.initializePlaybackDevice()
            }
            AudioPlayer.mixPlaybackMemory(buffer, 0)
            AudioPlayer.startPlayback()
        }) {
            Text(text = "Start playback")
        }

        Button(onClick = {
            AudioPlayer.stopPlayback()
            // AudioPlayer.uninitializePlaybackDevice()
            trackNumber += (trackNumber + 1) % 3
        }) {
            Text(text = "Stop playback")
        }
    }
}
