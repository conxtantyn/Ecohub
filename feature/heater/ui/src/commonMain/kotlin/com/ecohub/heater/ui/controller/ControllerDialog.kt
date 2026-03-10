package com.ecohub.heater.ui.controller

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import com.ecohub.heater.domain.model.Metrics

@Composable
fun ControllerDialog(
    state: ControllerViewModel.State,
    hasConflict: State<Boolean>,
    onResolve: (Metrics) -> Unit
) {
    val handleResolve by rememberUpdatedState(onResolve)
    if (hasConflict.value) {
        val conflict = state as ControllerViewModel.State.Conflict
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Conflict Detected") },
            text = {
                Text("The technician has updated the temperature to ${conflict.current.temperature}°C. You tried to set it to ${conflict.target.temperature}°C. What would you like to do?")
            },
            confirmButton = {
                Button(onClick = { handleResolve(conflict.current) }) {
                    Text("Overwrite")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    handleResolve(conflict.target)
                }) {
                    Text("Keep Theirs")
                }
            }
        )
    }
}
