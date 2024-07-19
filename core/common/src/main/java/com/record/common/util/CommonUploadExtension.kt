package com.record.common.util

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import java.io.FileOutputStream
import java.io.IOException

fun getVideoFrameAt1Sec(videoPath: String, outputImagePath: String) {
    val retriever = MediaMetadataRetriever()
    try {
        retriever.setDataSource(videoPath)
        val timeUs = 1 * 1000000
        val bitmap: Bitmap? =
            retriever.getFrameAtTime(timeUs.toLong(), MediaMetadataRetriever.OPTION_CLOSEST_SYNC)

        if (bitmap != null) {
            saveBitmapAsJpeg(bitmap, outputImagePath)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        retriever.release()
    }
}

fun saveBitmapAsJpeg(bitmap: Bitmap, outputPath: String) {
    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(outputPath)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            out?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
