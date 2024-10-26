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
import com.record.upload.searchplace.SearchPlaceScreen
import com.record.upload.searchplace.SearchPlaceScreenRoute

fun NavController.navigateToUpload() {
    navigate(UploadRoute.ROUTE)
}

fun NavController.navigateToSearchPlace() {
    navigate(UploadRoute.SEARCH_PLACE)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit = {},
    navigateToSearchPlace:()->Unit,
    onShowSnackBar: (String, SnackBarType) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(route = UploadRoute.ROUTE) {
        VideoPickerRoute(
            paddingValues = padding,
            popBackStack = popBackStack,
            onShowSnackBar = onShowSnackBar,
            navigateToSearchPlace=navigateToSearchPlace
        )
    }
    composable(route = UploadRoute.SEARCH_PLACE) {
        SearchPlaceScreenRoute(
            paddingValues = padding
        )
    }
}

object UploadRoute {
    const val ROUTE = "upload"
    const val SEARCH_PLACE = "search_place"
}
