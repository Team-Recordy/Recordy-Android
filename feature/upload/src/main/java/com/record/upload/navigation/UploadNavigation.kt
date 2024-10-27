package com.record.upload.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.upload.VideoPickerRoute
import com.record.upload.addPlace.AddPlaceScreenRoute
import com.record.upload.confirmplace.ConfirmAddPlaceScreenRoute
import com.record.upload.searchplace.SearchPlaceScreenRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToUpload(upload: UploadRoute.Upload?) {
    navigate(route = upload ?: UploadRoute.Upload())
}
fun NavController.navigateToUpload(confirmPlace: UploadRoute.ConfirmPlace) {
    navigate(route = confirmPlace)
}

fun NavController.navigateToSearchPlace() {
    navigate(route = UploadRoute.SearchPlace)
}
fun NavController.navigateToAddPlace() {
    navigate(route = UploadRoute.AddPlace)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit = {},
    navigateToSearchPlace: () -> Unit,
    navigateToUpload: (UploadRoute.Upload) -> Unit,
    onShowSnackBar: (String, SnackBarType) -> Unit,
    navigateToAddPlace: () -> Unit,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<UploadRoute.Upload> { entry ->
        VideoPickerRoute(
            paddingValues = padding,
            popBackStack = popBackStack,
            onShowSnackBar = onShowSnackBar,
            navigateToSearchPlace = {
                navigateToSearchPlace()
            },
        )
    }

    composable<UploadRoute.SearchPlace> { entry ->
        SearchPlaceScreenRoute(
            paddingValues = padding,
            popBackStackArgument = { selectedPlace ->
              navigateToUpload(selectedPlace)
            },
            navigateToAddPlace = navigateToAddPlace,
            popBackStack = popBackStack
        )
    }

    composable<UploadRoute.AddPlace> { entry ->
        AddPlaceScreenRoute(
            paddingValues = padding,
            navigateToConfirmPlace=navigateToConfirmPlace,
            popBackStack = popBackStack
        )
    }

    composable<UploadRoute.ConfirmPlace> { entry ->
        ConfirmAddPlaceScreenRoute(
            popBackStack = popBackStack
        )
    }
}
@Serializable
sealed class UploadRoute {
    @Serializable
    data class Upload(
        val screenId:String="Upload",
        val id: Long = 0,
        val address: String = "",
        val name: String = "",
    )

    @Serializable
    data object SearchPlace : UploadRoute()

    @Serializable
    data object AddPlace : UploadRoute()

    @Serializable
    data class ConfirmPlace(
        val placeId:String="",
        val address: String = "",
        val name: String = "",
        val longitude: Double=0.0,
        val latitude: Double=0.0,
    )
}
