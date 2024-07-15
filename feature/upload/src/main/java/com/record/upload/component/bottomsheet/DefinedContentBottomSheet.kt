package com.record.upload.component.bottomsheet

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.component.bottomsheet.RecordyBottomSheet
import com.record.designsystem.component.button.RecordyChipButton
import com.record.designsystem.component.button.RecordyMiddleButton
import com.record.designsystem.theme.RecordyTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DefinedContentBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    isSheetOpen: Boolean,
    onDismissRequest: () -> Unit,
    contentList: List<String>,
    selectedList: List<String>,
    onClickContentChip: (String) -> Unit,
) {
    RecordyBottomSheet(
        isSheetOpen = isSheetOpen,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(RecordyTheme.colors.gray08)
                .padding(horizontal = 16.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_drag_handle),
                contentDescription = null,
            )
            Text(
                text = "키워드 선택",
                style = RecordyTheme.typography.subtitle,
                color = RecordyTheme.colors.gray01,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = "해당 공간은\n" + "어떤 느낌인가요?",
                style = RecordyTheme.typography.title1,
                color = RecordyTheme.colors.gray01,
                modifier = Modifier.padding(top = 22.dp, bottom = 11.dp),
            )
            Text(
                text = "키워드 1~3개 선택 시, 프로필에서 취향을 분석해 드립니다.",
                style = RecordyTheme.typography.caption1,
                color = RecordyTheme.colors.gray03,
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 50.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                contentList.forEach { chip ->
                    RecordyChipButton(
                        text = chip,
                        isActive = if (selectedList.filter { it == chip }.isNotEmpty()) true else false,
                        onClick = { onClickContentChip(chip) },
                    )
                }
            }
            RecordyMiddleButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 44.dp)
                    .align(Alignment.CenterHorizontally),
                text = "적용하기",
                enabled = if (selectedList.isNotEmpty()) true else false,
                onClick = {
                    if (selectedList.isNotEmpty()) Log.d("enable btn", "true") else Log.d("disable btn", "false")
                },
            )
        }
    }
}
