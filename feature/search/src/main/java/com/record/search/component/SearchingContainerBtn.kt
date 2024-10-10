package com.record.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
fun SearchingContainerBtn(
    modifier: Modifier = Modifier,
    exhibitionName: String,
    location: String,
    venue: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = RecordyTheme.colors.black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 10.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp)
                ) {
                    Text(text = venue, style = RecordyTheme.typography.caption1M, color = RecordyTheme.colors.gray05)
                    Image(
                        painter = painterResource(id = R.drawable.ic_eclipse_16),
                        contentDescription = "Circle Icon",
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 4.dp)
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
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
