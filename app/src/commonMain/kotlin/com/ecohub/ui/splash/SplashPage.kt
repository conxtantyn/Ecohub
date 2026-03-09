package com.ecohub.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ecohub.ui.theme.DesignTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplashPage() {
    Box(modifier = Modifier.fillMaxSize())
}

@Preview
@Composable
fun PreviewSplashPage() {
    DesignTheme {
        SplashPage()
    }
}
