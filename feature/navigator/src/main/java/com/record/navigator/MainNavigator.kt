package com.record.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.home.navigation.HomeRoute
import com.record.home.navigation.navigateHome
import com.record.login.navigation.LoginRoute
import com.record.mypage.navigation.navigateMypage
import com.record.profile.navigation.navigateProfile
import com.record.video.navigation.navigateVideo
import com.record.video.navigation.navigateVideoDetail

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = HomeRoute.route
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainNavTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainNavTab::find)

    fun navigate(tab: MainNavTab) {
        val navOptions = navOptions {
            popUpTo(HomeRoute.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainNavTab.HOME -> navController.navigateHome(navOptions)
            MainNavTab.VIDEO -> navController.navigateVideo(navOptions)
            MainNavTab.MYPAGE -> navController.navigateMypage(navOptions)
        }
    }

    fun navigateHome() {
        navController.navigate(HomeRoute.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateLogin() {
        navController.navigate(LoginRoute.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateMypage() {
        navController.navigateMypage(navOptions { })
    }

    fun navigateVideoDetail(snackBarType: SnackBarType, videoId: Int) {
        navController.navigateVideoDetail(
            snackBarType = snackBarType,
            id = videoId,
        )
    }

    fun navigateProfile(id: Int) {
        navController.navigateProfile(navOptions { })
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination(HomeRoute.route)) {
            navController.popBackStack()
        }
    }

    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainNavTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
