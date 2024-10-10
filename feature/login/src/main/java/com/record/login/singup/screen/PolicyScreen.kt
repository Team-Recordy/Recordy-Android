package com.record.login.singup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.login.singup.SignUpState

@Composable
fun PolicyScreen(
    padding: PaddingValues = PaddingValues(horizontal = 16.dp),
    uiState: SignUpState,
    onCheckAllClick: () -> Unit,
    onCheckServiceClick: () -> Unit,
    onCheckPolicyClick: () -> Unit,
    onCheckAgeClick: () -> Unit,
    onMoreClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Spacer(modifier = Modifier.height(54.dp))
        Text(
            text = "비스킷 이용을 위해 \n필수 약관에 동의해 주세요.",
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
                .padding(padding),
            style = RecordyTheme.typography.title1,
            color = RecordyTheme.colors.gray01,
        )
        Spacer(modifier = Modifier.height(32.dp))
        RecordyCheckAllBox(
            "전체 동의",
            padding = padding,
            checked = uiState.allChecked,
            onClickEvent = { onCheckAllClick() },
        )
        Spacer(modifier = Modifier.height(8.dp))
        RecordyCheckBox(
            "(필수) 서비스 이용약관 동의",
            padding = padding,
            checked = uiState.serviceTermsChecked,
            onClickEvent = { onCheckServiceClick() },
            moreUrl = "https://bohyunnkim.notion.site/e5c0a49d73474331a21b1594736ee0df?pvs=4",
            onMoreClick = onMoreClick,
        )
        RecordyCheckBox(
            "(필수) 개인정보 수집이용 동의",
            padding = padding,
            checked = uiState.privacyPolicyChecked,
            onClickEvent = { onCheckPolicyClick() },
            moreUrl = "https://bohyunnkim.notion.site/c2bdf3572df1495c92aedd0437158cf0?pvs=4",
            onMoreClick = onMoreClick,
        )
        RecordyCheckBox(
            "(필수) 만 14세 이상입니다",
            padding = padding,
            checked = uiState.ageChecked,
            onClickEvent = { onCheckAgeClick() },
        )
    }
}

@Composable
fun RecordyCheckBox(
    contentText: String = "",
    padding: PaddingValues,
    checked: Boolean = false,
    onClickEvent: () -> Unit,
    moreUrl: String? = null,
    onMoreClick: ((String) -> Unit)? = null,
) {
    Column {
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .height(40.dp)
                .clickable(onClick = onClickEvent, indication = null, interactionSource = remember { MutableInteractionSource() })
                .padding(start = 20.dp)
                .padding(vertical = 11.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_check_16),
                    contentDescription = null,
                    modifier = Modifier
                        .height(16.dp)
                        .padding(vertical = 1.dp)
                        .aspectRatio(1f),
                    tint = if (checked) RecordyTheme.colors.gray01 else RecordyTheme.colors.gray08,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = contentText,
                    style = RecordyTheme.typography.caption1R,
                    color = RecordyTheme.colors.gray02,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (moreUrl != null && onMoreClick != null) {
                    Text(
                        text = "더보기",
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .clickable(
                                onClick = { onMoreClick(moreUrl) },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                            )
                            .padding(end = 20.dp),
                        style = RecordyTheme.typography.caption1U.copy(color = RecordyTheme.colors.gray05),
                    )
                }
            }
        }
    }
}

@Composable
fun RecordyCheckAllBox(
    contentText: String = "",
    padding: PaddingValues,
    checked: Boolean = false,
    onClickEvent: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
            .height(54.dp)
            .background(RecordyTheme.colors.gray09, RoundedCornerShape(8.dp))
            .clickable(onClick = onClickEvent, indication = null, interactionSource = remember { MutableInteractionSource() })
            .padding(start = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_check_24),
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .padding(vertical = 2.dp)
                    .aspectRatio(1f),
                tint = if (checked) RecordyTheme.colors.gray01 else RecordyTheme.colors.gray08,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = contentText,
                style = RecordyTheme.typography.subtitle,
                color = RecordyTheme.colors.white,
            )
        }
    }
}

@Preview()
@Composable()
fun PreviewPolicyScreen() {
    RecordyTheme {
        Box(modifier = Modifier.background(color = RecordyTheme.colors.background)) {
            PolicyScreen(uiState = SignUpState(), onCheckAllClick = { /*TODO*/ }, onCheckServiceClick = { /*TODO*/ }, onCheckPolicyClick = { /*TODO*/ }, onCheckAgeClick = { /*TODO*/ }) {
            }
        }
    }
}
