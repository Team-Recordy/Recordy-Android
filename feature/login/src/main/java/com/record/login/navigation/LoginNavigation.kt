package com.record.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.login.LoginRoute

fun NavController.navigateLogin(navOptions: NavOptions) {
    navigate(LoginRoute.route, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = LoginRoute.route) {
        LoginRoute(
            padding = padding,
            modifier = modifier,
        )
    }
}

object LoginRoute {
    const val route = "Login"
}