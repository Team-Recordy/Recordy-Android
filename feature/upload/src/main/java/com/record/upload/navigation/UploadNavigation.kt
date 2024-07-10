package com.record.upload.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.upload.VideoPickerScreen

fun NavController.navigateUpload(navOptions: NavOptions) {
    navigate(UploadRoute.route, navOptions)
}

fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = UploadRoute.route) {
        VideoPickerScreen(
        )
    }
}

object UploadRoute {
    const val route = "upload"
}
