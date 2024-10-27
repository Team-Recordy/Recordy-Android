package com.record.upload.addPlace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.exhibition.model.PlaceUsingMap
import com.record.upload.navigation.UploadRoute


@Composable
fun ConfirmAddPlaceScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: ConfirmPlaceViewModel= hiltViewModel(),
) {
    ConfirmAddPlaceScreen(
        modifier = modifier,
    )
}

@Composable
fun ConfirmAddPlaceScreen(
    modifier: Modifier,
) {

    Column(
        modifier = modifier
            .background(color = RecordyTheme.colors.black)
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 28.dp),
    ) {
        TopNavigationBar(
            modifier = Modifier,
            title = "내용 작성",
            enableGradation = true,
            popBackStackEnable = true,
        )
        Text(text = "이 장소가 맞나요?")
        Box {
            Column {
                Text(text = "상호명")
                Text(text = "새콤달콤 마이쮸")
            }
        }
    }}