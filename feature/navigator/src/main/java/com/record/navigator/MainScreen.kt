package com.record.navigator

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
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
) {
    val context = LocalContext.current
    val navBackStackEntry by navigator.navController.currentBackStackEntryAsState()
    val currentDestination by remember(navBackStackEntry) {
        derivedStateOf { navBackStackEntry?.destination }
    }

    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            NavHost(
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
                    padding = innerPadding
                )

                mypageNavGraph(
                    padding = innerPadding
                )
            }
        },
        bottomBar = {
            MainBottomNavigationBar(
                visible = navigator.shouldShowBottomBar(),
                currentTab = navigator.currentTab,
                entries = MainNavTab.entries.toImmutableList(),
                onClickItem = navigator::navigate
            )
        }
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
                color = RecordyTheme.colors.black
            )
            Row(
                modifier = Modifier.height(56.dp)
                    .background(color = RecordyTheme.colors.white),
            ) {
                entries.forEach { tab ->
                    tab.run {
                        NavItem(
                            selected = tab == currentTab,
                            label = stringResource(id = titleId),
                            iconId = iconId,
                            onClick = { onClickItem(tab) }
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
        val icon = if (selected) iconId else iconId
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
        )
        Text(
            text = label,
            color = RecordyTheme.colors.black
        )
    }
}