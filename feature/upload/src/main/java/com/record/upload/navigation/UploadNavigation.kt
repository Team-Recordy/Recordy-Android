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
fun NavController.navigateSelectedVideo() {
    navigate(UploadRoute.SELECTED_VIDEO)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateSelectedVideo: () -> Unit,
) {
    composable(route = UploadRoute.ROUTE) {
        VideoPickerRoute(
            paddingValues = padding,
            navigateSelectedVideo = navigateSelectedVideo,
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
