package com.ecohub.heater.ui.display

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ecohub.ui.theme.DesignTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DisplayPage(
    title: String,
    status: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = status.uppercase(),
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun PreviewDisplayPage(
    modifier: Modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
) {
    DesignTheme {
        DisplayPage(
            title = "25°C",
            status = "Current temperature",
            color = MaterialTheme.colorScheme.outline,
            modifier = modifier
        )
    }
}
