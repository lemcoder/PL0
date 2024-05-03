package pl.lemanski.pandaloop.presentation.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.lemanski.pandaloop.domain.model.visual.Component
import pl.lemanski.pandaloop.presentation.visual.components.utils.Composable

@Composable
fun StartScreen(
    tempoPicker: Component.TempoPicker,
    timeSignatureSelect: Component.TextSelect,
    createLoopButton: Component.Button
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            createLoopButton.Composable()
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            tempoPicker.Composable()
            timeSignatureSelect.Composable()
        }
    }
}