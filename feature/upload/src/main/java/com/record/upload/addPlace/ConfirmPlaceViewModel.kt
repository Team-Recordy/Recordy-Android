package com.record.upload.addPlace

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.record.exhibition.repository.ExhibitionRepository
import com.record.exhibition.repository.SearchRepository
import com.record.ui.base.BaseViewModel
import com.record.upload.model.RecordInfo
import com.record.upload.navigation.UploadRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPlaceViewModel @Inject constructor(
    private val exhibitionRepository: ExhibitionRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ConfirmPlaceState, SearchSideEffect>(
    initialState = ConfirmPlaceState(),
) {
    val place = savedStateHandle.toRoute<UploadRoute.ConfirmPlace>()
    init {
        Log.d("searchConfirmPlace","$place")
    }
    fun upload() = viewModelScope.launch {
        exhibitionRepository.postPlace(uiState.value.place)
    }
    fun setPlace() {
        intent {
            copy(place = place)
        }
    }
}
