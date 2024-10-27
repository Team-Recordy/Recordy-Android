package com.record.upload.searchplace

import androidx.lifecycle.viewModelScope
import com.record.exhibition.repository.SearchRepository
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : BaseViewModel<SearchState, SearchSideEffect>(
    initialState = SearchState(),
) {
    init {
        viewModelScope.launch {
            uiState.debounce(200).collectLatest {
                searchRepository.searchExhibition(it.query).onSuccess {
                    intent {
                        copy(filteredItems = it.toImmutableList())
                    }
                }
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        intent {
            copy(query = newQuery)
        }
    }
}
