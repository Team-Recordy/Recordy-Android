package com.record.upload.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.upload.VideoPickerRoute

fun NavController.navigateToUpload() {
    navigate(UploadRoute.ROUTE)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.uploadNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit = {},
    onShowSnackBar: (String, SnackBarType) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(route = UploadRoute.ROUTE) {
        VideoPickerRoute(
            paddingValues = padding,
            popBackStack = popBackStack,
            onShowSnackBar = onShowSnackBar,
        )
    }
}

object UploadRoute {
    const val ROUTE = "upload"
}
