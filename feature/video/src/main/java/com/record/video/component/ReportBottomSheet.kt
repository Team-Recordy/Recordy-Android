package com.record.video.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.component.videoplayer.BottomSheetNavigation
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.extension.customClickable
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import com.record.video.model.ReportReason
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBottomSheet(
    modifier: Modifier = Modifier,
    id: Long,
    isMine: Boolean,
    onClickLinkCopy: () -> Unit,
    onClickReport: (Long, String, String) -> Unit,
    onClickDelete: (Long) -> Unit,
    isShowBottomSheet: Boolean,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var currentState by remember { mutableStateOf(BottomSheetNavigation.DEFAULT) }
    var inputValidation by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    var selectedReason by remember { mutableStateOf("") }
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
                    currentState = BottomSheetNavigation.DEFAULT
                    textValue = ""
                    selectedReason = ""
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
                        /*Row(
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
                        }*/
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
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .customClickable {
                                            currentState = BottomSheetNavigation.REPORT
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
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }

                BottomSheetNavigation.REPORT -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(RecordyTheme.colors.gray10),
                    ) {
                        Text(
                            text = "영상 신고",
                            style = RecordyTheme.typography.title3,
                            color = RecordyTheme.colors.gray01,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(vertical = 15.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                        )
                        ReportReason.entries.forEach {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .customClickable {
                                        currentState = BottomSheetNavigation.ELSE
                                        selectedReason = it.reasonText
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = it.reasonText,
                                    style = RecordyTheme.typography.body1M,
                                    color = RecordyTheme.colors.gray01,
                                    modifier = Modifier
                                        .padding(start = 20.dp),
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(56.dp))
                    }
                }

                BottomSheetNavigation.ELSE -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(RecordyTheme.colors.gray10),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_angle_left_24),
                                tint = RecordyTheme.colors.gray01,
                                contentDescription = "back",
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .customClickable {
                                        currentState = BottomSheetNavigation.REPORT
                                        inputValidation = false
                                    }
                                    .padding(vertical = 15.dp)
                                    .padding(start = 20.dp, end = 20.dp),
                            )

                            Text(
                                text = selectedReason,
                                style = if (selectedReason.length >= 14) RecordyTheme.typography.subtitle else RecordyTheme.typography.title3,
                                color = RecordyTheme.colors.gray01,
                                modifier = Modifier.align(Alignment.Center),
                            )

                            Text(
                                text = "완료",
                                style = RecordyTheme.typography.subtitle,
                                color = RecordyTheme.colors.gray01,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .customClickable {
                                        val reasonEnum = ReportReason.fromReasonText(selectedReason)
                                        onClickReport(id, reasonEnum.name, textValue)
                                        coroutineScope.launch {
                                            sheetState.hide()
                                        }.invokeOnCompletion {
                                            onDismiss()
                                            currentState = BottomSheetNavigation.DEFAULT
                                            textValue = ""
                                            selectedReason = ""
                                        }
                                    }
                                    .padding(vertical = 15.dp)
                                    .padding(end = 20.dp, start = 20.dp),
                            )
                        }

                        ReportTextField(textValue = textValue, isValidate = inputValidation) {
                            textValue = it
                            if (it.isNotEmpty()) {
                                inputValidation = true
                            } else {
                                inputValidation = false
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReportTextField(
    textValue: String,
    modifier: Modifier = Modifier,
    maxCharCount: Int = 30,
    isValidate: Boolean = false,
    placeholder: String = "신고 사유를 작성해 주세요. (선택)",
    onValueChange: (String) -> Unit,
) {
    Column(modifier = modifier.padding(20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isValidate) RecordyTheme.colors.viskitYellow40 else RecordyTheme.colors.gray05,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            BasicTextField(
                value = textValue,
                onValueChange = {
                    if (it.length <= maxCharCount) {
                        onValueChange(it)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = RecordyTheme.typography.body2L.copy(color = RecordyTheme.colors.gray02),
                cursorBrush = SolidColor(RecordyTheme.colors.gray02),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
            )

            if (textValue.isEmpty()) {
                Text(
                    text = placeholder,
                    style = RecordyTheme.typography.body2L,
                    color = RecordyTheme.colors.gray06,
                    modifier = Modifier
                        .padding(14.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "허위 신고일 경우, 신고가 취소될 수 있습니다.",
                style = RecordyTheme.typography.caption2M,
                color = RecordyTheme.colors.gray02,
            )
            Text(
                text = "${textValue.length} / $maxCharCount",
                style = RecordyTheme.typography.caption2M,
                color = RecordyTheme.colors.gray02,
            )
        }
    }
}
