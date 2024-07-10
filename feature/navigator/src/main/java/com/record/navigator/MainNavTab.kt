package com.record.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.record.designsystem.R
import com.record.home.navigation.HomeRoute
import com.record.mypage.navigation.MypageRoute
import com.record.upload.navigation.UploadRoute
import com.record.video.navigation.VideoRoute

enum class MainNavTab(
    @DrawableRes val iconId: Int,
    @DrawableRes val selectedIconId: Int,
    @StringRes val titleId: Int,
    val route: String,
) {
    HOME(
        iconId = R.drawable.ic_tab_place_28,
        selectedIconId = R.drawable.ic_tab_place_pressed_28,
        titleId = R.string.home,
        route = HomeRoute.route,
    ),
    VIDEO(
        iconId = R.drawable.ic_tab_video_28,
        selectedIconId = R.drawable.ic_tab_video_pressed_28,
        titleId = R.string.video,
        route = VideoRoute.route,
    ),
    UPLOAD(
        iconId = R.drawable.ic_tab_record_28,
        selectedIconId = R.drawable.ic_tab_record_pressed_28,
        titleId = R.string.upload,
        route = UploadRoute.route,
    ),
    MYPAGE(
        iconId = R.drawable.ic_tab_profile_28,
        selectedIconId = R.drawable.ic_tab_profile_pressed_28,
        titleId = R.string.profile,
        route = MypageRoute.route,
    ),
    ;

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainNavTab? {
            return entries.find { it.route == route }
        }
    }
}
