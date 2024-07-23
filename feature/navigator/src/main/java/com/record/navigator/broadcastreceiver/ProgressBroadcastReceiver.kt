package com.record.navigator.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build

class ProgressBroadcastReceiver(
    private val onProgressUpdate: (Int) -> Unit,
    private val onUploadStart: () -> Unit,
    private val onUploadStop: () -> Unit,
    private val onUploadSuccess: () -> Unit,
    private val onUploadFailure: () -> Unit,
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_UPLOAD_PROGRESS -> {
                val progress = intent.getIntExtra(EXTRA_PROGRESS, 0)
                onProgressUpdate(progress)
            }
            ACTION_UPLOAD_START -> {
                onUploadStart()
            }
            ACTION_UPLOAD_END -> {
                onUploadStop()
            }
            ACTION_UPLOAD_SUCCESS -> {
                onUploadSuccess()
            }
            ACTION_UPLOAD_FAILURE -> {
                onUploadFailure()
            }
        }
    }

    companion object {
        const val ACTION_UPLOAD_PROGRESS = "com.record.video.UPLOAD_PROGRESS"
        const val ACTION_UPLOAD_START = "com.record.video.UPLOAD_START"
        const val ACTION_UPLOAD_END = "com.record.video.UPLOAD_END"
        const val ACTION_UPLOAD_SUCCESS = "com.record.video.UPLOAD_SUCCESS"
        const val ACTION_UPLOAD_FAILURE = "com.record.video.UPLOAD_FAILURE"
        const val EXTRA_PROGRESS = "progress"

        fun register(context: Context, receiver: ProgressBroadcastReceiver) {
            val intentFilter = IntentFilter().apply {
                addAction(ACTION_UPLOAD_PROGRESS)
                addAction(ACTION_UPLOAD_START)
                addAction(ACTION_UPLOAD_END)
                addAction(ACTION_UPLOAD_SUCCESS)
                addAction(ACTION_UPLOAD_FAILURE)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
            } else {
                context.registerReceiver(receiver, intentFilter)
            }
        }

        fun unregister(context: Context, receiver: ProgressBroadcastReceiver) {
            context.unregisterReceiver(receiver)
        }
    }
}
