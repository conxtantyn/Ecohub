package com.ecohub.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecohub.heater.ui.controller.PreviewControllerPage
import com.ecohub.heater.ui.display.PreviewDisplayPage
import com.ecohub.ui.theme.DesignTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardPage(
    top: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val updatedTop by rememberUpdatedState(top)
    val updatedContent by rememberUpdatedState(content)
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .fillMaxSize()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        updatedTop()
        Spacer(modifier = Modifier.padding(16.dp))
        updatedContent()
    }
}

@Preview
@Composable
fun PreviewDashboardPage() {
    DesignTheme {
        DashboardPage({
            PreviewDisplayPage(modifier = Modifier)
        }) {
            PreviewControllerPage(modifier = Modifier)
        }
    }
}
