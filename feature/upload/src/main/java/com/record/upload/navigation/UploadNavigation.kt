package com.record.upload.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.upload.SelectedVideoRoute
import com.record.upload.VideoPickerRoute

fun NavController.navigateUpload(navOptions: NavOptions) {
    navigate(UploadRoute.ROUTE, navOptions)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = UploadRoute.ROUTE) {
        VideoPickerRoute(
            paddingValues = padding,
        )
    }
    composable(route = UploadRoute.SELECTED_VIDEO) {
        SelectedVideoRoute(
            paddingValues = padding,
        )
    }
}

object UploadRoute {
    const val ROUTE = "upload"
    const val SELECTED_VIDEO = "selected-video"
}
