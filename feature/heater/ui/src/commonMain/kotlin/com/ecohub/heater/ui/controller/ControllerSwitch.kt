package com.ecohub.heater.ui.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.ecohub.heater.domain.model.Mode
import com.ecohub.ui.theme.DesignTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ControllerSwitch(
    text: String,
    mode: State<Mode>,
    modifier: Modifier = Modifier,
    onToggleMode: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.then(modifier)
            .clip(RoundedCornerShape(36.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 6.dp)
        )
        Switch(
            checked = mode.value == Mode.AUTOMATIC,
            onCheckedChange = onToggleMode,
            modifier = Modifier.scale(0.8f)
        )
    }
}

@Preview
@Composable
fun PreviewControllerSwitch() {
    DesignTheme {
        val mode = remember { mutableStateOf(Mode.MANUAL) }
        ControllerSwitch(
            text = "Auto",
            mode = mode,
            modifier = Modifier.padding(16.dp),
        ) {}
    }
}