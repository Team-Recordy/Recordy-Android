package com.record.designsystem.component.videoplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBottomSheet(
    modifier: Modifier = Modifier,
    id: Long,
    isMine: Boolean,
    onClickLinkCopy: () -> Unit,
    onClickReport: () -> Unit,
    onClickDelete: (Long) -> Unit,
    isShowBottomSheet: Boolean,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var currentState by remember { mutableStateOf(BottomSheetNavigation.DEFAULT) }

    LaunchedEffectWithLifecycle {
        if (isShowBottomSheet) {
            coroutineScope.launch {
                sheetState.show()
            }
        }
    }
    if (isShowBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    onDismiss()
                }
            },
            sheetState = sheetState,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RecordyTheme.colors.gray10),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BottomSheetDefaults.DragHandle()
                }
            },
        ) {
            when (currentState) {
                BottomSheetNavigation.DEFAULT -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(RecordyTheme.colors.gray10),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .customClickable {
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_link_24),
                                contentDescription = "link",
                                tint = RecordyTheme.colors.gray01,
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .padding(vertical = 12.dp),
                            )
                            Text(
                                text = "링크 복사하기",
                                style = RecordyTheme.typography.body1M,
                                color = RecordyTheme.colors.gray01,
                                modifier = Modifier
                                    .padding(start = 16.dp),
                            )
                        }
                        when (isMine) {
                            true -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .customClickable {
                                            onClickDelete(id)
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_trashcan_24),
                                        contentDescription = "delete",
                                        tint = RecordyTheme.colors.gray01,
                                        modifier = Modifier
                                            .padding(start = 20.dp)
                                            .padding(vertical = 12.dp),
                                    )
                                    Text(
                                        text = "삭제하기",
                                        style = RecordyTheme.typography.body1M,
                                        color = RecordyTheme.colors.gray01,
                                        modifier = Modifier
                                            .padding(start = 16.dp),
                                    )
                                }
                            }

                            false -> {
                                /*Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .customClickable {

                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_error_24),
                                        contentDescription = "report",
                                        tint = RecordyTheme.colors.alert02,
                                        modifier = Modifier
                                            .padding(start = 20.dp)
                                            .padding(vertical = 12.dp),
                                    )
                                    Text(
                                        text = "신고하기",
                                        style = RecordyTheme.typography.body1M,
                                        color = RecordyTheme.colors.alert02,
                                        modifier = Modifier
                                            .padding(start = 16.dp),
                                    )
                                }*/
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }

                BottomSheetNavigation.REPORT -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "영상 신고",
                            style = RecordyTheme.typography.title3,
                            color = RecordyTheme.colors.gray01,
                            modifier = Modifier
                                .padding(vertical = 15.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                        )
                    }
                }

                BottomSheetNavigation.ELSE -> {
                }
            }
        }
    }
}
