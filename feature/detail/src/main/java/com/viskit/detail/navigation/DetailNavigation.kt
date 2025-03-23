package com.viskit.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.detail.DetailRoute
import com.record.detail.navigation.DetailRoute.PLACE_ID
import com.record.model.VideoType

fun NavController.navigateDetail(placeId: Long, navOptions: NavOptions) {
    navigate(DetailRoute.detailRoute(placeId.toString()), navOptions)
}

fun NavGraphBuilder.detailNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToUpload: () -> Unit,
    navigateToVideo: (VideoType, Long, Long) -> Unit,
) {
    composable(
        route = DetailRoute.detailRoute(
            "{$PLACE_ID}",
        ),
    ) {
        DetailRoute(
            padding = padding,
            modifier = modifier,
            navigateToVideo = navigateToVideo,
            navigateToUpload = navigateToUpload,
        )
    }
}

object DetailRoute {
    const val route = "place-detail"
    const val PLACE_ID = "place-id"
    fun detailRoute(placeId: String) = "$route/$placeId"
}
