package com.record.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.detail.DetailRoute

fun NavController.navigateDetail(navOptions: NavOptions) {
    navigate(DetailRoute.route, navOptions)
}

fun NavGraphBuilder.detailNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = DetailRoute.route) {
        DetailRoute(modifier = modifier)
    }
}

object DetailRoute {
    const val route = "search"
}
