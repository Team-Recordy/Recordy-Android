package com.viskit.upload.confirmplace

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.record.exhibition.model.PlaceUsingMap
import com.record.exhibition.repository.ExhibitionRepository
import com.record.model.AlertInfo
import com.record.ui.base.BaseViewModel
import com.record.upload.navigation.UploadRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPlaceViewModel @Inject constructor(
    private val exhibitionRepository: ExhibitionRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ConfirmPlaceState, ConfirmPlaceSideEffect>(
    initialState = ConfirmPlaceState(),
) {
    val place2 = savedStateHandle.toRoute<UploadRoute.ConfirmPlace>()

    init {
        setPlace()
    }

    fun upload() = viewModelScope.launch {
        exhibitionRepository.postPlace(uiState.value.place)
    }
    fun setPlace() {
        intent {
            copy(
                place = PlaceUsingMap(
                    platformPlaceId = place2.placeId,
                    address = place2.address,
                    longitude = place2.longitude,
                    latitude = place2.latitude,
                    name = place2.name,
                ),
            )
        }
    }
    fun showUploadPlaceDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = true,
                title = "${uiState.value.place.name}",
                subTitle = "등록할까요?",
                negativeButtonLabel = "취소",
                positiveButtonLabel = "등록",
            ),
        )
    }
    fun hideUploadDialog() = intent {
        copy(
            alertInfo = AlertInfo(
                showDialog = false,
            ),
        )
    }
    fun popBackStack() {
        postSideEffect(ConfirmPlaceSideEffect.PopBackStack)
    }
    fun navigateToUpload() {
        postSideEffect(ConfirmPlaceSideEffect.NavigateToUpload)
    }
}
