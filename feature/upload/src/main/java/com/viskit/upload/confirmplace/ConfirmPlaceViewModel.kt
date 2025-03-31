package com.viskit.upload.confirmplace

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.amplitude.android.Amplitude
import com.viskit.exhibition.model.PlaceUsingMap
import com.viskit.exhibition.repository.ExhibitionRepository
import com.viskit.model.AlertInfo
import com.viskit.ui.base.BaseViewModel
import com.viskit.upload.navigation.UploadRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPlaceViewModel @Inject constructor(
    private val exhibitionRepository: ExhibitionRepository,
    private val amplitude: Amplitude,
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

    fun amplitudeTrack(name: String, value: Any?) {
        amplitude.track("Upload", mutableMapOf(name to value))
    }
}
