package com.record.navigator

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.record.designsystem.component.snackbar.RecordySnackBar
import com.record.designsystem.theme.RecordyTheme
import com.record.home.navigation.homeNavGraph
import com.record.login.navigation.loginNavGraph
import com.record.mypage.navigation.mypageNavGraph
import com.record.profile.navigation.profileNavGraph
import com.record.upload.navigation.uploadNavGraph
import com.record.video.navigation.videoNavGraph
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navBackStackEntry by navigator.navController.currentBackStackEntryAsState()
    val currentDestination by remember(navBackStackEntry) {
        derivedStateOf { navBackStackEntry?.destination }
    }

    Scaffold(
        content = { innerPadding ->
            NavHost(
                modifier = modifier
                    .background(color = RecordyTheme.colors.background)
                    .fillMaxSize(),
                navController = navigator.navController,
                startDestination = navigator.startDestination,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                loginNavGraph(
                    padding = innerPadding,
                )

                homeNavGraph(
                    padding = innerPadding,
                )

                profileNavGraph(
                    padding = innerPadding,
                )

                uploadNavGraph(
                    padding = innerPadding,
                )

                videoNavGraph(
                    padding = innerPadding,
                    onShowSnackBar = viewModel::onShowSnackbar,
                )

                mypageNavGraph(
                    padding = innerPadding,
                )
            }
            RecordySnackBar(
                visible = state.snackBarVisible,
                message = state.snackBarMessage,
                snackBarType = state.snackBarType,
                bottomPadding = state.snackBarBottomPadding.dp,
            )
        },
        bottomBar = {
            MainBottomNavigationBar(
                visible = navigator.shouldShowBottomBar(),
                currentTab = navigator.currentTab,
                entries = MainNavTab.entries.toImmutableList(),
                onClickItem = navigator::navigate,
            )
        },
    )
}

@Composable
private fun MainBottomNavigationBar(
    visible: Boolean,
    currentTab: MainNavTab?,
    entries: ImmutableList<MainNavTab>,
    onClickItem: (MainNavTab) -> Unit?,
) {
    AnimatedVisibility(
        visible = visible,
    ) {
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = RecordyTheme.colors.gray05,
            )
            Row(
                modifier = Modifier
                    .height(72.dp)
                    .background(color = RecordyTheme.colors.gray08),
            ) {
                entries.forEach { tab ->
                    tab.run {
                        NavItem(
                            selected = tab == currentTab,
                            label = stringResource(id = titleId),
                            iconId = iconId,
                            selectedIconId = selectedIconId,
                            onClick = { onClickItem(tab) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.NavItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    label: String,
    @DrawableRes iconId: Int,
    @DrawableRes selectedIconId: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(enabled = true, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val icon = if (selected) selectedIconId else iconId
        Image(
            modifier = modifier.padding(bottom = 2.dp),
            painter = painterResource(id = icon),
            contentDescription = label,
        )
        Text(
            text = label,
            color = if (selected) RecordyTheme.colors.main else RecordyTheme.colors.gray04,
            style = RecordyTheme.typography.caption1,
        )
    }
}
