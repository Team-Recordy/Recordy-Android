package com.record.navigator

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.record.designsystem.component.snackbar.SnackBarType
import com.record.designsystem.theme.RecordyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var uploadResultReceiver: BroadcastReceiver

    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        uploadResultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    val message = it.getStringExtra("message")
                    message?.let { msg ->
                        if (msg=="success") viewModel.onShowSnackbar("업로드가 완료되었습니다.",SnackBarType.CHECK)
                        else  viewModel.onShowSnackbar("업로드가 실패했습니다.",SnackBarType.WARNING)
                    }
                }
            }
        }
        val filter = IntentFilter("com.example.UPLOAD_RESULT")
        registerReceiver(uploadResultReceiver, filter)
        enableEdgeToEdge()
        setContent {
            RecordyTheme {
                val isDarkTheme = isSystemInDarkTheme()
                val view = LocalView.current
                if (!isDarkTheme) {
                    WindowCompat.getInsetsController(window, view).apply {
                        isAppearanceLightStatusBars = false
                        isAppearanceLightNavigationBars = false
                        window.navigationBarColor = Color.Black.toArgb()
                    }
                }
                MainScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.systemBars.only(WindowInsetsSides.Bottom),
                        ),
                )
            }
        }
    }
}
