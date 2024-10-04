package com.record.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.record.designsystem.theme.RecordyTheme
import kotlinx.collections.immutable.ImmutableList
import java.util.Date

@Composable
fun ListScreen(
    exhibitionItems: ImmutableList<Pair<String, Date>>,
    exhibitionCount: Int,
    selectedChip: ChipTab,
    onChipSelected: (ChipTab) -> Unit,
    onItemClick: (String) -> Unit
) {
    val lazyGridState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 24.dp),
            ) {
                ChipRow(
                    chipTabs = ChipTab.entries,
                    selectedChip = selectedChip,
                    onChipSelected = onChipSelected
                )

                Box(
                    modifier = Modifier.fillMaxWidth()
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

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            if (exhibitionCount == 0) {
                item {
                    EmptyDataScreen(
                        message = "\n진행 중인 전시가 없어요.",
                        showButton = false,
                    )
                }
            } else {
                items(exhibitionItems) { item ->
                    val (name, date) = item
                    ExhibitionItem(name = name, date = date, onButtonClick = { })
                }
            }
        }
    }
}

@Composable
fun ExhibitionItem(name: String, date: Date, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(RecordyTheme.colors.gray10)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Column {
                Text(
                    text = name,
                    style = RecordyTheme.typography.subtitle,
                    color = RecordyTheme.colors.gray01,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${date}", // 날짜 형식은 별도 처리 가능
                    style = RecordyTheme.typography.caption1M,
                    color = RecordyTheme.colors.gray05,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
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
