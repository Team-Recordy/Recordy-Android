package com.record.upload

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun UploadRoute(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    VideoPickerScreen(padding = padding)
}

@Composable
fun VideoPickerScreen(
    padding:PaddingValues,
    /*
    //ToDo uiState,Onclick   
    * */
){
    Box(modifier = Modifier.fillMaxSize()) {
        Text("upload")
    }
}
