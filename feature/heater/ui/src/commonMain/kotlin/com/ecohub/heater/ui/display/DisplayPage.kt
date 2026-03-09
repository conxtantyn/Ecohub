package com.ecohub.heater.ui.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ecohub.ui.theme.DesignTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DisplayPage(
    title: String,
    status: String,
    color: Color
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Current Status",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = status,
            style = MaterialTheme.typography.bodyLarge,
            color = color
        )
    }
}

@Preview
@Composable
fun PreviewDisplayPage() {
    DesignTheme {
        DisplayPage(
            title = "25°C",
            status = "Active",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
