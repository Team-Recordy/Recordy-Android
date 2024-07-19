package com.record.designsystem.component.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.record.designsystem.component.shadow.cardShadow

@Composable
fun ShadowIcon(
    modifier: Modifier = Modifier,
    resourceId: Int,
    color: Color,
    contentDescription: String,
) {
    Box(
        modifier = modifier.cardShadow(
            borderRadius = 15.dp,
            blurRadius = 45.dp,
        ),
    ) {
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = contentDescription,
            tint = color,
        )
    }
}
