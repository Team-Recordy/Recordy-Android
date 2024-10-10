package com.record.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _filteredItems = MutableStateFlow<List<Exhibition>>(emptyList())
    val filteredItems: StateFlow<List<Exhibition>> = _filteredItems.asStateFlow()

    private val items = listOf(
        Exhibition("국립현대미술관", "서울 종로구", "전시회장"),
        Exhibition("국으로 시작하는 단어", "서울 종로구", "전시회장"),
        Exhibition("서울 예술의 전당", "서울 서초구", "전시회장"),
        Exhibition("D Museum", "서울 용산구", "미술관"),
    )

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
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
            _filteredItems.update { result }
        }
    }
}
