package com.viskit.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.viskit.designsystem.R
import com.viskit.home.navigation.HomeRoute
import com.viskit.mypage.navigation.MypageRoute
import com.viskit.search.navigation.SearchRoute
import com.viskit.upload.navigation.UploadRoute
import com.viskit.video.navigation.VideoRoute

enum class MainNavTab(
    @DrawableRes val iconId: Int,
    @StringRes val titleId: Int,
    val route: String,
) {
    HOME(
        iconId = R.drawable.ic_tab_home,
        titleId = R.string.home,
        route = HomeRoute.route,
    ),
    SEARCH(
        iconId = R.drawable.ic_tab_search,
        titleId = R.string.search,
        route = SearchRoute.route,
    ),
    UPLOAD(
        iconId = R.drawable.ic_tab_upload,
        titleId = R.string.upload,
        route = UploadRoute.Upload().name,
    ),
    VIDEO(
        iconId = R.drawable.ic_tab_video,
        titleId = R.string.video,
        route = VideoRoute.route,
    ),
    MYPAGE(
        iconId = R.drawable.ic_tab_mypage,
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
