package com.record.video.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.video.VideoRoute

fun NavController.navigateVideo(navOptions: NavOptions) {
    navigate(VideoRoute.route, navOptions)
}

fun NavGraphBuilder.videoNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    onShowSnackBar: (String, SnackBarType) -> Unit,
) {
    composable(route = VideoRoute.route) {
        VideoRoute(
            padding = padding,
            modifier = modifier,
            onShowSnackbar = onShowSnackBar,
        )
    }
}

object VideoRoute {
    const val route = "video"
}
