package com.record.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.record.designsystem.theme.RecordyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordyBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit = {},
    shape: Shape = RectangleShape,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = shape,
            onDismissRequest = {
                scope.launch { sheetState.hide() }
                onDismissRequest()
            },
            containerColor = Color(0xFFE8E8E8),
            dragHandle = null,
            windowInsets = WindowInsets(0, 0, 0, 0),
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RecordyBottomSheetPreview() {
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    RecordyTheme {
        RecordyBottomSheet(
            isSheetOpen = true,
            onDismissRequest = { isSheetOpen = !isSheetOpen },
            content = {},
        )
    }
}
