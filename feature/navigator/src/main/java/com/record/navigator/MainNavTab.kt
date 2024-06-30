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
    @StringRes val titleId: Int,
    val route: String,
) {
    HOME(
        iconId = R.drawable.ic_launcher_background,
        titleId = R.string.app_name,
        route = HomeRoute.route
    ),
    UPLOAD(
        iconId = R.drawable.ic_launcher_background,
        titleId = R.string.app_name,
        route = UploadRoute.route
    ),
    VIDEO(
        iconId = R.drawable.ic_launcher_background,
        titleId = R.string.app_name,
        route = VideoRoute.route
    ),
    MYPAGE(
        iconId = R.drawable.ic_launcher_background,
        titleId = R.string.app_name,
        route = MypageRoute.route
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