package com.record.ui.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

@Composable
fun LaunchedEffectWithLifecycle(
    key1: Any? = Unit,
    key2: Any? = Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend CoroutineScope.() -> Unit = {},
) {
    LaunchedEffect(key1 = key1, key2 = key2) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            action()
        }
    }
}
