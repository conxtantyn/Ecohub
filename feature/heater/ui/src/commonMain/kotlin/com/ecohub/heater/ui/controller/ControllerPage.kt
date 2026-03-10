package com.ecohub.heater.ui.controller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecohub.heater.domain.model.Mode
import com.ecohub.ui.theme.DesignTheme
import ecohub.feature.heater.ui.generated.resources.Res
import ecohub.feature.heater.ui.generated.resources.mode
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ControllerPage(
    mode: State<Mode>,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier,
    onToggleMode: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ControllerSwitch(
            text = stringResource(Res.string.mode),
            mode = mode,
            onToggleMode = onToggleMode
        )
        ControllerButton(
            onDecrease = onDecrement,
            onIncrement = onIncrement,
            modifier = Modifier.padding(top = 48.dp),
        )
    }
}

@Preview
@Composable
fun PreviewControllerPage(
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(24.dp)
) {
    DesignTheme {
        val mode = remember { mutableStateOf(Mode.MANUAL) }
        ControllerPage(
            mode = mode,
            modifier = modifier,
            onDecrement = {},
            onIncrement = {}
        ) {}
    }
}
