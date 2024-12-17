package com.record.designsystem.component.searchcomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme

@Composable
fun SearchingContainerBtn(
    modifier: Modifier = Modifier,
    exhibitionName: String,
    location: String,
    venue: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(color = RecordyTheme.colors.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                ) {
                    Text(
                        text = "$venue • $location",
                        style = RecordyTheme.typography.caption1M,
                        color = RecordyTheme.colors.gray05,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = exhibitionName,
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.gray01,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_angle_16),
                contentDescription = "Arrow Icon",
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

@Preview
@Composable
fun SearchingContainerBtnPreview() {
    RecordyTheme {
        SearchingContainerBtn(
            exhibitionName = "전시회명",
            location = "서울",
            venue = "예술의 전당",
        )
    }
}
