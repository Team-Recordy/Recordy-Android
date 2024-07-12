package com.record.upload.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.upload.CompletedUploadRoute
import com.record.upload.DefinedContentRoute
import com.record.upload.SelectedVideoRoute
import com.record.upload.VideoPickerRoute

fun NavController.navigateUpload(navOptions: NavOptions) {
    navigate(UploadRoute.ROUTE, navOptions)
}
fun NavController.navigateSelectedVideo() {
    navigate(UploadRoute.SELECTED_VIDEO)
}
fun NavController.navigateDefinedContent() {
    navigate(UploadRoute.DEFINED_CONTENT)
}
fun NavController.navigateCompletedUpload() {
    navigate(UploadRoute.COMPLETED_UPLOAD)
}

fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateSelectedVideo: () -> Unit,
    navigateDefinedContent: () -> Unit,
    navigateCompletedUpload: () -> Unit,
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
            navigateDefinedContent = navigateDefinedContent,
        )
    }
    composable(route = UploadRoute.DEFINED_CONTENT) {
        DefinedContentRoute(
            paddingValues = padding,
            navigateCompletedUpload = navigateCompletedUpload,
        )
    }
    composable(route = UploadRoute.COMPLETED_UPLOAD) {
        CompletedUploadRoute(
            paddingValues = padding,
        )
    }
}

object UploadRoute {
    const val ROUTE = "upload"
    const val SELECTED_VIDEO = "selected-video"
    const val DEFINED_CONTENT = "defined-content"
    const val COMPLETED_UPLOAD = "completed-upload"
}
