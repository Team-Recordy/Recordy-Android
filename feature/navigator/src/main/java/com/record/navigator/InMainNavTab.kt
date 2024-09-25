package com.record.navigator

import com.record.mypage.navigation.MypageRoute
import com.record.profile.navigation.ProfileRoute
import com.record.video.navigation.VideoRoute

enum class InMainNavTab(
    val route: String,
) {
    VIDEO_DETAIL(VideoRoute.videoDetailRoute),
    FOLLOWING(MypageRoute.followingRoute),
    FOLLOWER(MypageRoute.followerRoute),
    PROFILE(ProfileRoute.route),
    ;
    companion object {
        operator fun contains(route: String): Boolean {
            return InMainNavTab.entries.map { it.route }.contains(route)
        }

        fun find(route: String): InMainNavTab? {
            return InMainNavTab.entries.find { it.route == route }
        }
    }
}
