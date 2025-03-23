package com.viskit.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.viskit.detail.navigation.navigateDetail
import com.viskit.home.navigation.HomeRoute
import com.viskit.home.navigation.navigateHome
import com.viskit.login.navigation.LoginRoute
import com.viskit.login.navigation.SignupRoute
import com.viskit.model.VideoType
import com.viskit.mypage.navigation.navigateMypage
import com.viskit.mypage.navigation.navigateToFollower
import com.viskit.mypage.navigation.navigateToFollowing
import com.viskit.profile.navigation.ProfileRoute
import com.viskit.profile.navigation.navigateProfile
import com.viskit.search.navigation.navigateSearch
import com.viskit.setting.navigate.navigateProfileEdit
import com.viskit.setting.navigate.navigateSetting
import com.viskit.upload.navigation.UploadRoute
import com.viskit.upload.navigation.UploadRouteObject
import com.viskit.upload.navigation.navigateToAddPlace
import com.viskit.upload.navigation.navigateToSearchPlace
import com.viskit.upload.navigation.navigateToUpload
import com.viskit.video.navigation.VideoRoute
import com.viskit.video.navigation.navigateVideo
import com.viskit.video.navigation.navigateVideoDetail

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
        _currentTab = tab
        val navOptions = navOptions {
            popUpTo(HomeRoute.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = when (tab) {
                MainNavTab.HOME -> false
                MainNavTab.VIDEO -> false
                MainNavTab.MYPAGE -> true
                MainNavTab.SEARCH -> true
                MainNavTab.UPLOAD -> true
            }
        }

        when (tab) {
            MainNavTab.HOME -> navController.navigateHome(navOptions)
            MainNavTab.VIDEO -> navController.navigateVideo(navOptions)
            MainNavTab.MYPAGE -> navController.navigateMypage(navOptions)
            MainNavTab.SEARCH -> navController.navigateSearch(navOptions)
            MainNavTab.UPLOAD -> navController.navigateToUpload()
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
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateMypage() {
        navController.navigateMypage(navOptions { })
    }
    fun navigateToVideo() {
        navController.navigate(VideoRoute.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
    fun navigateVideoDetail(videoType: VideoType, videoId: Long, placeId: Long = 0, userId: Long = 0) {
        navController.navigateVideoDetail(
            videoType = videoType,
            videoId = videoId,
            userId = userId,
            placeId = placeId,
        )
    }
    fun navigateToUpload() {
        navController.navigate(route = UploadRouteObject.route) {
            popUpTo(HomeRoute.route) {
                inclusive = false
                saveState = false
            }
        }
    }

    fun navigateToUploadTab() {
        navController.navigateToUpload()
    }

    fun navigateToConfirmPlace(confirmPlace: UploadRoute.ConfirmPlace) {
        navController.navigateToUpload(confirmPlace)
    }
    fun navigateToSearchPlace() {
        navController.navigateToSearchPlace()
    }
    fun navigateToAddPlace() {
        navController.navigateToAddPlace()
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

    fun navigateProfileEdit() {
        navController.navigateProfileEdit(navOptions { })
    }
    fun navigateDetail(id: Long) {
        navController.navigateDetail(placeId = id, navOptions { })
    }

    fun navigateSearch() {
        navController.navigateSearch(navOptions { })
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination(HomeRoute.route)) {
            navController.popBackStack()
        }
    }

    fun popBackStackArgument() {
        navController.popBackStack(
            route = UploadRouteObject.route,
            inclusive = false,
        )
    }

    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        if (currentRoute == UploadRoute.Upload().name) return false
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
