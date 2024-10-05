package com.record.detail.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import kotlinx.collections.immutable.ImmutableList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListScreen(
    exhibitionItems: ImmutableList<Triple<String, Date, Date>>,
    exhibitionCount: Int,
    selectedChip: ChipTab,
    onChipSelected: (ChipTab) -> Unit,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                    ) {
                        ChipRow(
                            chipTabs = ChipTab.entries,
                            selectedChip = selectedChip,
                            onChipSelected = onChipSelected
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Text(
                                text = buildCountText(exhibitionCount),
                                style = RecordyTheme.typography.caption1R,
                            )
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            if (exhibitionCount == 0) {
                item {
                    EmptyDataScreen(
                        message = "\n진행 중인 전시가 없어요.",
                        showButton = false,
                    )
                }
            } else {
                items(exhibitionItems) { item ->
                    val (name, startDate, endDate) = item
                    Column {
                        ExhibitionItem(
                            name = name,
                            startDate = startDate,
                            endDate = endDate,
                            onButtonClick = { }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ExhibitionItem(name: String, startDate: Date, endDate: Date, onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(RecordyTheme.colors.gray10)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name,
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.gray01,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formatDate(startDate, endDate),
                    style = RecordyTheme.typography.caption1M,
                    color = RecordyTheme.colors.gray05,
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_more_informations),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onButtonClick() }
                )
            }
        }
    }
}



fun formatDate(startDate: Date, endDate: Date): String {
    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    return "${dateFormat.format(startDate)}~${dateFormat.format(endDate)}"
}

@Composable
fun ChipRow(
    chipTabs: List<ChipTab>,
    selectedChip: ChipTab,
    onChipSelected: (ChipTab) -> Unit
) {
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chipTabs) { chipTab ->
            Chip(
                text = chipTab.displayName,
                isSelected = chipTab == selectedChip,
                onClick = { onChipSelected(chipTab) }
            )
        }
    }
}

@Composable
fun Chip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(if (isSelected) RecordyTheme.colors.gray01 else RecordyTheme.colors.gray09)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) RecordyTheme.colors.background else RecordyTheme.colors.gray03,
            style = RecordyTheme.typography.caption1R
        )
    }
}
