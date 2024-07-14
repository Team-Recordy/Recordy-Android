package com.record.upload.component.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefinedContentBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
) {
}
