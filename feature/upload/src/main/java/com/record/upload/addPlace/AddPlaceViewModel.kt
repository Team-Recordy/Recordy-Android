package com.record.upload.addPlace

import androidx.lifecycle.viewModelScope
import com.record.exhibition.model.PlaceUsingMap
import com.record.exhibition.repository.SearchRepository
import com.record.ui.base.BaseViewModel
import com.record.upload.navigation.UploadRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : BaseViewModel<AddPlaceState, AddPlaceSideEffect>(
    initialState = AddPlaceState(),
) {
    init {
        viewModelScope.launch {
            uiState.debounce(200).collectLatest {
                searchRepository.searchPlace(it.query).onSuccess {
                    intent {
                        copy(filteredItems = it.toImmutableList())
                    }
                }.onFailure {
                    intent {
                        copy(filteredItems = emptyList<PlaceUsingMap>().toImmutableList())
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
    fun popBackStack() {
        postSideEffect(AddPlaceSideEffect.PopBackStack)
    }
    fun navigateToConfirmPlace(confirmPlace: UploadRoute.ConfirmPlace) {
        postSideEffect(AddPlaceSideEffect.NavigateToConfirmPlaceScreen(confirmPlace))
    }
}
