package com.record.setting.navigate

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.record.setting.ProfileEditRoute
import com.record.setting.SettingRoute

fun NavController.navigateSetting(navOptions: NavOptions) {
    navigate(SettingRoute.route, navOptions)
}

fun NavController.navigateProfileEdit(navOptions: NavOptions) {
    navigate(SettingRoute.editProfile)
}

fun NavGraphBuilder.settingNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToProfileEdit: () -> Unit,
) {
    composable(route = SettingRoute.route) {
        SettingRoute(
            padding = padding,
            modifier = modifier,
            popBackStack = popBackStack,
            navigateToLogin = navigateToLogin,
            navigateToProfileEdit = navigateToProfileEdit,
        )
    }
    composable(route = SettingRoute.editProfile) {
        ProfileEditRoute(padding, modifier, popBackStack)
    }
}

object SettingRoute {
    const val route = "setting"
    const val editProfile = "edit"
}
