package com.record.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.profile.ProfileRoute

fun NavController.navigateProfile(navOptions: NavOptions) {
    navigate(ProfileRoute.route, navOptions)
}

fun NavGraphBuilder.profileNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = ProfileRoute.route) {
        ProfileRoute(
            padding = padding,
            modifier = modifier,
        )
    }
}

object ProfileRoute {
    const val route = "Profile"
}
