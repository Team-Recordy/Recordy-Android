package com.record.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme

@Composable
fun SearchedContainerBtn(
    modifier: Modifier = Modifier,
    exhibitionName: String,
    location: String,
    venue: String,
    type: List<String>,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = RecordyTheme.colors.black),
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
            ) {
                Column(
                    modifier = modifier
                        .weight(1f),
                ) {
                    Spacer(modifier = modifier.padding(top = 16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp),
                    ) {
                        Text(text = venue, style = RecordyTheme.typography.caption1M, color = RecordyTheme.colors.gray05)
                        Image(
                            painter = painterResource(id = R.drawable.ic_eclipse_16),
                            contentDescription = "Circle Icon",
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 4.dp),
                        )
                        Text(text = location, style = RecordyTheme.typography.caption1M, color = RecordyTheme.colors.gray05)
                    }
                    Text(text = exhibitionName, style = RecordyTheme.typography.subtitle, color = RecordyTheme.colors.gray01)
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_angle_16),
                    contentDescription = "Arrow Icon",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically),
                )
            }

            Column(
                modifier = modifier.padding(horizontal = 8.dp),
            ) {
                type.take(3).forEachIndexed { index, title ->
                    if (index > 0) Spacer(modifier = modifier.height(8.dp))
                    ExhibitionTitle(type = title)
                }
            }

            Spacer(modifier = modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun SearchedContainerBtnPreview() {
    RecordyTheme {
        SearchedContainerBtn(
            exhibitionName = "전시회명",
            location = "위치",
            venue = "장소",
            type = listOf("전시회", "공간", "장소"),
        )
    }
}
