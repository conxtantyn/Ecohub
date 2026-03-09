package com.ecohub.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import ecohub.core.ui.generated.resources.ManropeRegular
import ecohub.core.ui.generated.resources.PoppinsRegular
import ecohub.core.ui.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun bodyFontFamily() : FontFamily {
    return FontFamily(
        Font(
            resource = Res.font.ManropeRegular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
    )
}

@Composable
fun headerFontFamily() : FontFamily {
    return FontFamily(
        Font(
            resource = Res.font.PoppinsRegular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
    )
}
