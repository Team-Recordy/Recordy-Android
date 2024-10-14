package com.record.search

import androidx.lifecycle.viewModelScope
import com.record.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel<SearchState, SearchSideEffect>(
    initialState = SearchState()
) {
    private val items = listOf(
        Exhibition("국립현대미술관", "서울 종로구", "전시회장", listOf("미술전시회1", "전시회2", "전시회3")),
        Exhibition("국으로 시작하는 단어", "서울 종로구", "전시회장", listOf("미술", "현대미술")),
        Exhibition("서울 예술의 전당", "서울 서초구", "전시회장", listOf("음악")),
        Exhibition("D Museum", "서울 용산구", "미술관", listOf("사진", "디자인")),
    )

    fun onQueryChanged(newQuery: String) {
        intent {
            copy(query = newQuery)
        }
        filterItems(newQuery)
    }

    private fun filterItems(query: String) {
        viewModelScope.launch {
            val result = if (query.isEmpty()) {
                items
            } else {
                items.filter {
                    it.exhibitionName.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true) ||
                        it.venue.contains(query, ignoreCase = true)
                }
            }
            intent {
                copy(filteredItems = result)
            }
        }
    }
}
