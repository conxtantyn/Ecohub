package com.ecohub.heater.ui.controller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecohub.heater.domain.model.Mode
import com.ecohub.ui.theme.DesignTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ControllerPage(
    mode: State<Mode>,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    onToggleMode: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Temperature Control",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.size(32.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onDecrement) {
                Text("-", style = MaterialTheme.typography.headlineLarge)
            }
            Box(
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 16.dp)
            )
            IconButton(onClick = onIncrement) {
                Text("+", style = MaterialTheme.typography.headlineLarge)
            }
        }
        Spacer(modifier = Modifier.size(48.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Collaborative Mode")
            Switch(
                checked = mode.value == Mode.AUTOMATIC,
                onCheckedChange = onToggleMode
            )
        }
    }
}

@Preview
@Composable
fun PreviewControllerPage() {
    DesignTheme {
        val mode = remember { mutableStateOf(Mode.MANUAL) }
        ControllerPage(
            mode = mode,
            onDecrement = {},
            onIncrement = {}
        ) {}
    }
}
