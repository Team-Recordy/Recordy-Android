package com.record.video.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.model.VideoType
import com.record.video.VideoRoute
import com.record.video.videodetail.VideoDetailRoute

fun NavController.navigateVideo(navOptions: NavOptions) {
    navigate(VideoRoute.route, navOptions)
}

fun NavController.navigateVideoDetail(videoType: VideoType, id: Long) {
    navigate(VideoRoute.detailRoute(videoType.toString(), id.toString()))
}

fun NavGraphBuilder.videoNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    onShowSnackBar: (String, SnackBarType) -> Unit,
    navigateToMypage: () -> Unit,
    navigateToProfile: (Int) -> Unit,
) {
    composable(route = VideoRoute.route) {
        VideoRoute(
            padding = padding,
            modifier = modifier,
            onShowSnackbar = onShowSnackBar,
            navigateToMypage = navigateToMypage,
            navigateToProfile = navigateToProfile,
        )
    }
    composable(
        route = VideoRoute.detailRoute("{${VideoRoute.VIDEO_TYPE_ARG_NAME}}", "{${VideoRoute.VIDEO_ID_ARG_NAME}}"),
    ) {
        VideoDetailRoute(
            padding = padding,
            modifier = modifier,
            onShowSnackbar = onShowSnackBar,
            navigateToMypage = navigateToMypage,
            navigateToUserProfile = navigateToProfile,
        )
    }
}

object VideoRoute {
    const val route = "video"
    const val VIDEO_TYPE_ARG_NAME = "video-type"
    const val VIDEO_ID_ARG_NAME = "video-id"
    fun detailRoute(type: String, id: String) = "detail/$type/$id"
}
