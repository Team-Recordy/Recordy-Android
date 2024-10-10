package com.record.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.record.designsystem.R
import com.record.designsystem.theme.RecordyTheme
import com.record.search.component.SearchBox
import com.record.search.component.SearchingContainerBtn

@Composable
fun SearchRoute(
    modifier: Modifier,
) {
    val exampleItems = List(10) { "Item $it" }
    SearchScreen(modifier = modifier, items = exampleItems)
}

@Composable
fun SearchScreen(
    modifier: Modifier,
    items: List<String>
) {
    var query by remember { mutableStateOf("") }

    val filteredItems = items.filter {
        it.contains(query, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .background(color = RecordyTheme.colors.black)
    ) {
        SearchBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp, horizontal = 16.dp),
            query = query,
            onQueryChange = { newQuery ->
                query = newQuery
            }
        )

        if (query.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_tab_record_pressed_28), //
                    contentDescription = "Icon",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically)
                        .padding(end = 12.dp)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "공간뿐만 아니라 원하는 전시회를 찾고 싶다면?",
                        style = RecordyTheme.typography.caption1M,
                        color = RecordyTheme.colors.gray05
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp)
                    ) {
                        Text(
                            text = "\'전시회명\'",
                            style = RecordyTheme.typography.subtitle,
                            color = RecordyTheme.colors.viskitYellow300
                        )

                        Text(
                            text = "을 검색해 보세요!",
                            style = RecordyTheme.typography.subtitle,
                            color = RecordyTheme.colors.gray01
                        )
                    }
                }
            }
        } else if (filteredItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = RecordyTheme.colors.black)
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 106.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_recordy_logo), // 임시
                    contentDescription = "Empty Icon",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 18.dp),
                )

                Text(
                    text = "검색 결과가 없어요.\n" +
                        "검색어가 정확한지 확인해주세요!",
                    style = RecordyTheme.typography.title2,
                    color = RecordyTheme.colors.gray01,
                    modifier = Modifier.padding(horizontal = 51.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn {
                items(filteredItems) { item ->
                    SearchingContainerBtn(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun SearchRoutePreview() {
    RecordyTheme {
        SearchRoute(modifier = Modifier.fillMaxWidth())
    }
}
