package com.record.ui.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

/**
 * @param rippleEnabled
 * @param rippleColor
 * @param runIf clickable 조건
 * @param singleClick 중복 클릭
 * @param onClick
 * @return clickable 이 적용된 [Modifier]
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.customClickable(
    rippleEnabled: Boolean = true,
    rippleColor: Color? = null,
    runIf: Boolean = true,
    singleClick: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)?,
) = runIf(runIf) {
    composed {
        val multipleEventsCutter = remember { MultipleEventsCutter.get() }

        combinedClickable(
            onClick = {
                onClick?.let {
                    if (singleClick) {
                        multipleEventsCutter.processEvent {
                            it()
                        }
                    } else {
                        it()
                    }
                }
            },
            onLongClick = onLongClick,
            indication = rememberRipple(
                color = rippleColor ?: Color.Unspecified,
            ).takeIf {
                rippleEnabled
            },
            interactionSource = remember { MutableInteractionSource() },
        )
    }
}
