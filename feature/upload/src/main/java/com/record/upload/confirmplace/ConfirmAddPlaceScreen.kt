package com.record.upload.confirmplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.record.designsystem.component.button.RecordyButton
import com.record.designsystem.component.dialog.RecordyDialog
import com.record.designsystem.component.navbar.TopNavigationBar
import com.record.designsystem.theme.RecordyTheme
import com.record.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ConfirmAddPlaceScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: ConfirmPlaceViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToUpload: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is ConfirmPlaceSideEffect.PopBackStack -> popBackStack()

                is ConfirmPlaceSideEffect.NavigateToUpload -> {
                    popBackStack()
                    popBackStack()
                }
            }
        }
    }
    ConfirmAddPlaceScreen(
        modifier = modifier,
        state = state,
        onClickConfirm = {
            viewModel.upload()
            viewModel.navigateToUpload()
        },
        popBackStack = viewModel::popBackStack,
        hideExitUploadDialog = viewModel::hideUploadDialog,
        shoeUploadPlaceDialog = viewModel::showUploadPlaceDialog,
    )
}

@Composable
fun ConfirmAddPlaceScreen(
    modifier: Modifier,
    onClickConfirm: () -> Unit,
    popBackStack: () -> Unit,
    hideExitUploadDialog: () -> Unit,
    shoeUploadPlaceDialog: () -> Unit,
    state: ConfirmPlaceState = ConfirmPlaceState(),
) {
    Column(
        modifier = modifier
            .background(color = RecordyTheme.colors.background)
            .padding(horizontal = 16.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            TopNavigationBar(
                modifier = Modifier,
                title = "장소 등록",
                enableGradation = true,
                popBackStackEnable = true,
                popBackStack = popBackStack,
            )
            Text(
                text = "이 장소가 맞나요?",
                color = RecordyTheme.colors.gray01,
                style = RecordyTheme.typography.title1,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
            )
            ConfirmPlaceInfo(
                modifier = Modifier.padding(top = 25.dp),
                title = "상호명",
                subTitle = state.place.name,
            )
            ConfirmPlaceInfo(
                modifier = Modifier.padding(top = 12.dp),
                title = "주소",
                subTitle = state.place.address,
            )
        }
        RecordyButton(
            text = "확인",
            enabled = true,
            onClick = {
                shoeUploadPlaceDialog()
            },
        )
    }
    if (state.alertInfo.showDialog) {
        RecordyDialog(
            graphicAsset = null,
            title = state.alertInfo.title,
            subTitle = state.alertInfo.subTitle,
            negativeButtonLabel = state.alertInfo.negativeButtonLabel,
            positiveButtonLabel = state.alertInfo.positiveButtonLabel,
            onDismissRequest = hideExitUploadDialog,
            onPositiveButtonClick = {
                onClickConfirm()
            },
        )
    }
}

@Composable
private fun ConfirmPlaceInfo(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(RecordyTheme.colors.gray10)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = title,
            color = RecordyTheme.colors.gray01,
            style = RecordyTheme.typography.subtitle,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Text(
            text = subTitle,
            color = RecordyTheme.colors.gray03,
            style = RecordyTheme.typography.body2M,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}
