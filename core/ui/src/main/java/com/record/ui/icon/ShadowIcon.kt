package com.record.ui.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ShadowIcon(
    modifier: Modifier = Modifier,
    resourceId: Int,
    color: Color,
    contentDescription: String,
) {
    Box(modifier = modifier) {
        Icon(
            modifier = Modifier
                .offset(x = (-1).dp, y = (1).dp)
                .blur(4.dp),
            painter = painterResource(id = resourceId),
            contentDescription = contentDescription,
            tint = Color.Black,
        )
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = contentDescription,
            tint = color,
        )
    }
}
