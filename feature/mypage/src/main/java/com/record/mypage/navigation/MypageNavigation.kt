package com.record.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.mypage.MypageRoute

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.route, navOptions)
}

fun NavGraphBuilder.mypageNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = MypageRoute.route) {
        MypageRoute(
            padding = padding,
            modifier = modifier,
        )
    }
}

object MypageRoute {
    const val route = "mypage"
}