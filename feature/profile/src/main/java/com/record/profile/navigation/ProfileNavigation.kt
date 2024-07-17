package com.record.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.record.profile.ProfileRoute

fun NavController.navigateProfile(id: Long) {
    navigate(ProfileRoute.profileRoute(id.toString()))
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

    composable(route = ProfileRoute.profileRoute("{${ProfileRoute.PROFILE_ID_ARG_NAME}}")) {
        ProfileRoute(
            padding = padding,
            modifier = modifier,
        )
    }
}

object ProfileRoute {
    const val route = "Profile"
    const val PROFILE_ID_ARG_NAME = "profile-id"
    fun profileRoute(userId: String) = "profile/$userId"
}
