package com.record.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.login.LoginRoute
import com.record.login.singup.SignUpRoute

fun NavController.navigateLogin(navOptions: NavOptions) {
    navigate(LoginRoute.route, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    composable(route = LoginRoute.route) {
        LoginRoute(
            padding = padding,
            modifier = modifier,
            navigateToHome = navigateToHome,
            navigateToSignUp = navigateToSignUp,
        )
    }

    composable(route = SignupRoute.route) {
        SignUpRoute(
            navigateToHome = navigateToHome,
        )
    }
}

object LoginRoute {
    const val route = "Login"
}

object SignupRoute {
    const val route = "Signup"
}
