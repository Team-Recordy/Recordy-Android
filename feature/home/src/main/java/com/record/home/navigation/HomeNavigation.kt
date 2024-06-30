package com.record.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = HomeRoute.route) {
        HomeRoute(
            padding = padding,
            modifier = modifier,
        )
    }
}

object HomeRoute {
    const val route = "home"
}