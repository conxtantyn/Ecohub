package com.ecohub.heater.ui.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.ecohub.heater.ui.shape.LeftCaret
import com.ecohub.heater.ui.shape.RightCaret
import com.ecohub.ui.theme.DesignTheme
import ecohub.feature.heater.ui.generated.resources.Res
import ecohub.feature.heater.ui.generated.resources.ic_arrow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ControllerButton(
    modifier: Modifier = Modifier,
    onDecrease: () -> Unit,
    onIncrement: () -> Unit,
) {
    Box(
        modifier = Modifier.then(modifier)
            .sizeIn(
                maxWidth = 180.dp,
                maxHeight = 180.dp
            ).shadow(
                elevation = 1.dp,
                shape = CircleShape,
                clip = false
            ).clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(96.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxHeight()
                        .weight(1f)
                        .clip(LeftCaret())
                        .clickable(onClick = onDecrease)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow),
                        contentDescription = "Decrease",
                        modifier = Modifier.rotate(180f)
                            .padding(start = 20.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxHeight()
                        .weight(1f)
                        .clip(RightCaret())
                        .clickable(onClick = onIncrement)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow),
                        contentDescription = "Increase",
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewControllerButton() {
    DesignTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            ControllerButton(
                modifier = Modifier.padding(24.dp),
                onDecrease = {}
            ) {}
        }
    }
}
