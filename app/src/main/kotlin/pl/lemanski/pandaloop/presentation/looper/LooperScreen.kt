package pl.lemanski.pandaloop.presentation.looper

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LooperScreen(
    text: String,
    onButtonClick: () -> Unit
) {
    Column {
        Button(onClick = onButtonClick) {
            Text(text = text)
        }
    }
}