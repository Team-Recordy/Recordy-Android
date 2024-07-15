package com.record.setting.navigate

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.setting.SettingRoute

fun NavController.navigateSetting(navOptions: NavOptions) {
    navigate(SettingRoute.route, navOptions)
}

fun NavGraphBuilder.settingNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable(route = SettingRoute.route) {
        SettingRoute(
            padding = padding,
            modifier = modifier,
        )
    }
}

object SettingRoute {
    const val route = "setting"
}
