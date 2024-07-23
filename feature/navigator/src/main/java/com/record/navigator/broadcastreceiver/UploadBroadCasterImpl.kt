package com.record.navigator.broadcastreceiver

import android.content.Context
import android.content.Intent
import com.record.common.intentprovider.UploadBroadCaster
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UploadBroadCasterImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : UploadBroadCaster {
    override fun sendUploadProgress(percentage: Int) {
        val intentUploadProgress = Intent(ProgressBroadcastReceiver.ACTION_UPLOAD_PROGRESS).apply {
            putExtra(ProgressBroadcastReceiver.EXTRA_PROGRESS, percentage)
        }
        context.sendBroadcast(intentUploadProgress)
    }

    override fun sendUploadStart() {
        val intentUploadStart = Intent(ProgressBroadcastReceiver.ACTION_UPLOAD_START)
        context.sendBroadcast(intentUploadStart)
    }

    override fun sendUploadEnd() {
        val intentUploadEnd = Intent(ProgressBroadcastReceiver.ACTION_UPLOAD_END)
        context.sendBroadcast(intentUploadEnd)
    }

    override fun sendUploadSuccess() {
        val intentUploadSuccess = Intent(ProgressBroadcastReceiver.ACTION_UPLOAD_SUCCESS)
        context.sendBroadcast(intentUploadSuccess)
    }

    override fun sendUploadFailure() {
        val intentUploadFailure = Intent(ProgressBroadcastReceiver.ACTION_UPLOAD_FAILURE)
        context.sendBroadcast(intentUploadFailure)
    }
}
