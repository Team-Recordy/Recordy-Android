package com.record.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.search.SearchRoute

fun NavController.navigateSearch(navOptions: NavOptions) {
    navigate(SearchRoute.route, navOptions)
}

fun NavGraphBuilder.searchNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToPlaceDetail: (Long) -> Unit = {},
) {
    composable(route = SearchRoute.route) {
        SearchRoute(
            modifier = modifier,
            navigateToPlaceDetail = navigateToPlaceDetail,
        )
    }
}

object SearchRoute {
    const val route = "search"
}
