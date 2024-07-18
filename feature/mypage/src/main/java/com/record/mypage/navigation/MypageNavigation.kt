package com.record.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.model.VideoType
import com.record.mypage.MypageRoute
import com.record.mypage.follow.FollowerRoute
import com.record.mypage.follow.FollowingRoute

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.route, navOptions)
}

fun NavController.navigateToFollowing() {
    navigate(MypageRoute.followingRoute)
}

fun NavController.navigateToFollower() {
    navigate(MypageRoute.followerRoute)
}

fun NavGraphBuilder.mypageNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit,
    navigateToFollowing: () -> Unit,
    navigateToFollower: () -> Unit,
    navigateToProfile: (Long) -> Unit,
    navigateToVideo: (VideoType, Long) -> Unit,
) {
    composable(route = MypageRoute.route) {
        MypageRoute(
            padding = padding,
            modifier = modifier,
            navigateToSetting = navigateToSetting,
            navigateToFollowing = navigateToFollowing,
            navigateToFollower = navigateToFollower,
            navigateToVideo = navigateToVideo,
        )
    }
    composable(route = MypageRoute.followingRoute) {
        FollowingRoute(
            padding = padding,
            modifier = modifier,
            navigateToProfile = navigateToProfile
        )
    }
    composable(route = MypageRoute.followerRoute) {
        FollowerRoute(
            padding = padding,
            modifier = modifier,
            navigateToProfile = navigateToProfile
        )
    }

    composable(route = MypageRoute.profileRoute) {
        FollowingRoute(
            padding = padding,
            modifier = modifier,
            navigateToProfile = navigateToProfile
        )
    }
}

object MypageRoute {
    const val route = "mypage"
    const val followerRoute = "follower"
    const val followingRoute = "following"
    const val profileRoute = "profile"
}
