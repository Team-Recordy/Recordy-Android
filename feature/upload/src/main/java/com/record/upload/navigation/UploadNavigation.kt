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
import com.record.upload.searchplace.SearchPlaceScreenRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToUpload(upload: UploadRoute.Upload?) {
    navigate(route = upload ?: UploadRoute.Upload())
}

fun NavController.navigateToSearchPlace() {
    navigate(route = UploadRoute.SearchPlace)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit = {},
    navigateToSearchPlace: () -> Unit,
    navigateToUpload: (UploadRoute.Upload) -> Unit,
    onShowSnackBar: (String, SnackBarType) -> Unit,
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
        )
    }
}

@Serializable
sealed class UploadRoute {
    @Serializable
    data class Upload(
        val id: Long = 0,
        val address: String = "",
        val name: String = "",
    )

    @Serializable
    data object SearchPlace : UploadRoute()
}
