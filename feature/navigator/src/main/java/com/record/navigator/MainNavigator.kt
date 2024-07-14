package com.record.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.record.home.navigation.HomeRoute
import com.record.home.navigation.navigateHome
import com.record.login.navigation.LoginRoute
import com.record.login.navigation.SignupRoute
import com.record.model.VideoType
import com.record.mypage.navigation.navigateMypage
import com.record.profile.navigation.ProfileRoute
import com.record.profile.navigation.navigateProfile
import com.record.video.navigation.navigateVideo
import com.record.video.navigation.navigateVideoDetail

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = LoginRoute.route
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    private var _currentTab: MainNavTab? = null

    val currentTab: MainNavTab?
        @Composable get() {
            val currentRoute = currentDestination?.route
            val mainTab = currentRoute?.let(MainNavTab::find)
            if (mainTab != null) {
                _currentTab = mainTab
            }
            return _currentTab
        }

    fun navigate(tab: MainNavTab) {
        val navOptions = navOptions {
            popUpTo(HomeRoute.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = when (tab) {
                MainNavTab.HOME -> false
                MainNavTab.VIDEO -> true
                MainNavTab.MYPAGE -> true
            }
        }

        _currentTab = tab

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

    fun navigateSignUp() {
        navController.navigate(SignupRoute.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    fun navigateMypage() {
        navController.navigateMypage(navOptions { })
    }

    fun navigateVideoDetail(videoType: VideoType, videoId: Long, keyword: String? = "all", userId: Long = 0) {
        navController.navigateVideoDetail(
            videoType = videoType,
            videoId = videoId,
            keyword = keyword,
            userId = userId,
        )
    }

    fun navigateToFollowing() {
        navController.navigateToFollowing()
    }

    fun navigateToFollower() {
        navController.navigateToFollower()
    }

    fun navigateProfile(id: Long) {
        navController.navigateProfile(id)
    }

    fun navigateSetting() {
        navController.navigateSetting(navOptions { })
    }

    fun popBackStackIfNotHome() {
        val homeRoute = HomeRoute.route
        if (navController.currentDestination?.route == homeRoute) {
            navController.popBackStack()
        } else {
            navController.popBackStack(homeRoute, inclusive = false)
        }
    }

    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainNavTab || currentRoute in InMainNavTab || currentRoute.contains("detail") || currentRoute.contains(
            ProfileRoute.route,
        )
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
