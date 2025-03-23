package com.viskit.upload.navigation

import android.os.Build
import android.os.Parcelable
import android.util.Log
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
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

fun NavController.navigateToUpload() {
    navigate(route = UploadRouteObject.route) {
        popUpTo(graph.startDestinationId) { // 시작 destination(a)까지 pop
            inclusive = false // a는 유지
            saveState = false // 상태 저장하지 않음
        }
    }
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
    navigateToUpload: () -> Unit,
    onShowSnackBar: (String, SnackBarType) -> Unit,
    navigateToAddPlace: () -> Unit,
    popBackStackArgument: (UploadRoute.Upload) -> Unit,
    navigateToConfirmPlace: (UploadRoute.ConfirmPlace) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(
        route = UploadRouteObject.route,
    ) { entry ->
        val a = entry.savedStateHandle.get<String>("{${UploadRouteObject.PLACE_ID}}")
        val b = entry.savedStateHandle.get<String>("{${UploadRouteObject.PLACE_NAME}}")
        val c = entry.savedStateHandle.get<String>("{${UploadRouteObject.PLACE_ADDRESS}}")
        Log.d("backStack", "$a $b $c")
        VideoPickerRoute(
            paddingValues = padding,
            popBackStack = popBackStack,
            onShowSnackBar = onShowSnackBar,
            navigateToSearchPlace = {
                navigateToSearchPlace()
            },
            id = a ?: "",
            address = b ?: "",
            name = c ?: "",
        )
    }

    composable<UploadRoute.SearchPlace> { entry ->
        SearchPlaceScreenRoute(
            paddingValues = padding,
            popBackStackArgument = { selectedPlace ->
                popBackStackArgument(selectedPlace)
            },
            navigateToAddPlace = navigateToAddPlace,
            popBackStack = popBackStack,
        )
    }

    composable<UploadRoute.AddPlace> { entry ->
        AddPlaceScreenRoute(
            paddingValues = padding,
            navigateToConfirmPlace = navigateToConfirmPlace,
            popBackStack = popBackStack,
        )
    }

    composable<UploadRoute.ConfirmPlace> { entry ->
        ConfirmAddPlaceScreenRoute(
            popBackStack = popBackStack,
            navigateToUpload = navigateToUpload,
        )
    }
}
object UploadRouteObject {
    const val route = "upload"
    const val PLACE_ID = "upload-id"
    const val PLACE_NAME = "upload-name"
    const val PLACE_ADDRESS = "upload-address"
}

@Serializable
sealed class UploadRoute {
    @Parcelize
    @Serializable
    data class Upload(
        val screenId: String = "Upload",
        val id: Long = 0,
        val address: String = "",
        val name: String = "",
    ) : UploadRoute(), Parcelable

    @Serializable
    data object SearchPlace : UploadRoute()

    @Serializable
    data object AddPlace : UploadRoute()

    @Serializable
    data class ConfirmPlace(
        val placeId: String = "",
        val address: String = "",
        val name: String = "",
        val longitude: Double = 0.0,
        val latitude: Double = 0.0,
    )
}
