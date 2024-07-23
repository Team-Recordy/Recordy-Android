package com.record.video

import com.record.common.util.ProgressListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.IOException

class ProgressRequestBody(
    private val file: File,
    private val contentType: MediaType?,
    private val listener: ProgressListener,
) : RequestBody() {
    override fun contentType(): MediaType? = contentType

    override fun contentLength(): Long = file.length()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val `in` = file.inputStream()
        var uploaded = 0L
        var lastProgress = -1
        `in`.use {
            var read: Int
            while (`in`.read(buffer).also { read = it } != -1) {
                uploaded += read
                sink.write(buffer, 0, read)
                val progress = (100 * uploaded / fileLength).toInt()

                if (progress != lastProgress) {
                    listener.onProgressUpdate(progress)
                    lastProgress = progress
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }
}
