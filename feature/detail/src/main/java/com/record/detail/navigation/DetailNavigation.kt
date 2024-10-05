package com.record.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.detail.DetailRoute
import com.record.model.VideoType

fun NavController.navigateDetail(navOptions: NavOptions) {
    navigate(DetailRoute.route, navOptions)
}

fun NavGraphBuilder.detailNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToUpload: () -> Unit,
    navigateToVideo: (VideoType, Long) -> Unit,
) {
    composable(route = DetailRoute.route) {
        DetailRoute(
            padding = padding,
            modifier = modifier,
            navigateToVideo = navigateToVideo,
            navigateToUplaod = navigateToUpload,
        )
    }
}

object DetailRoute {
    const val route = "search"
}
