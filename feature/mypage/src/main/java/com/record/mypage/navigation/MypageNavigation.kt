package com.record.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.mypage.MypageRoute
import com.record.mypage.follow.FollowerRoute
import com.record.mypage.follow.FollowingRoute

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.route, navOptions)
}

fun NavController.navigateToFollowing(navOptions: NavOptions){
    navigate(MypageRoute.followingRoute, navOptions)
}

fun NavController.navigateToFollower(navOptions: NavOptions){
    navigate(MypageRoute.followerRoute, navOptions)
}

fun NavGraphBuilder.mypageNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit,
    navigateToFollowing: () -> Unit,
    navigateToFollower: () -> Unit,
) {
    composable(route = MypageRoute.route) {
        MypageRoute(
            padding = padding,
            modifier = modifier,
            navigateToSetting = navigateToSetting,
            navigateToFollowing = navigateToFollowing,
            navigateToFollower = navigateToFollower,
        )
    }
    composable(route = MypageRoute.followingRoute){
        FollowingRoute(
            padding = padding,
            modifier = modifier,)
    }
    composable(route = MypageRoute.followerRoute){
        FollowerRoute(
            padding = padding,
            modifier = modifier,)
    }
}

object MypageRoute {
    const val route = "mypage"
    const val followerRoute = "follower"
    const val followingRoute = "following"
}
