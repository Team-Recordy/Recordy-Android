package com.record.upload.extension

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.abedelazizshe.lightcompressorlibrary.CompressionListener
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor
import com.abedelazizshe.lightcompressorlibrary.VideoQuality
import com.abedelazizshe.lightcompressorlibrary.config.Configuration
import com.abedelazizshe.lightcompressorlibrary.config.SaveLocation
import com.abedelazizshe.lightcompressorlibrary.config.SharedStorageConfiguration
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

fun getAllVideos(
    loadSize: Int,
    currentLocation: String?,
    context: Context,
): MutableList<GalleryVideo> {
    val galleryVideoList = mutableListOf<GalleryVideo>()
    val uriExternal: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Video.VideoColumns.DATA,
        MediaStore.Video.VideoColumns.DISPLAY_NAME, // 이름
        MediaStore.Video.VideoColumns.SIZE, // 크기
        MediaStore.Video.VideoColumns.DATE_TAKEN,
        MediaStore.Video.VideoColumns.DATE_ADDED, // 추가된 날짜
        MediaStore.Video.VideoColumns._ID,
    )
    val resolver = context.contentResolver

    var selection: String? = null
    var selectionArgs: Array<String>? = null

    val query = resolver.query(
        uriExternal,
        projection,
        selection,
        selectionArgs,
        "${MediaStore.Video.VideoColumns.DATE_ADDED} DESC",
    )

    query?.use { cursor ->
        if (cursor.moveToFirst()) {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME)
            val filePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATA)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_TAKEN)

            do {
                val id = cursor.getLong(idColumn)
                val filepath = cursor.getString(filePathColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val date = cursor.getString(dateColumn)
                val contentUri = ContentUris.withAppendedId(uriExternal, id)
                val duration = getVideoDuration(context, contentUri)
                galleryVideoList.add(
                    GalleryVideo(
                        id,
                        filepath = filepath,
                        uri = contentUri,
                        name = name,
                        date = date ?: "",
                        size = size,
                        duration = duration,
                    ),
                )
            } while (cursor.moveToNext())
        }
    }

    return galleryVideoList
}

fun getVideoDuration(context: Context, uri: Uri): Long {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(context, uri)
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val timeInMillisec = time?.toLongOrNull() ?: 0L
        timeInMillisec
    } catch (e: Exception) {
        e.printStackTrace()
        0L
    } finally {
        retriever.release()
    }
}
data class GalleryVideo(
    val id: Long,
    val filepath: String,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
    val duration: Long,
)

fun compressVideo(context: Context, videoUri: Uri, name: String, onSuccess: (String?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        VideoCompressor.start(
            context = context,
            uris = listOf(videoUri),
            isStreamable = false,
            sharedStorageConfiguration = SharedStorageConfiguration(
                saveAt = SaveLocation.movies,
                subFolderName = "compressed_videos",
            ),
            configureWith = Configuration(
                quality = VideoQuality.MEDIUM,
                isMinBitrateCheckEnabled = true,
                videoBitrateInMbps = 5,
                disableAudio = false,
                keepOriginalResolution = false,
                videoNames = listOf(name),
            ),
            listener = object : CompressionListener {
                override fun onProgress(index: Int, percent: Float) {
                    // 압축 진행 상황 업데이트
                    Log.d("video onProgress", "$index , $percent")
                }

                override fun onStart(index: Int) {
                    // 압축 시작 시 호출
                    Log.d("video onStart", "$index")
                }

                override fun onSuccess(index: Int, size: Long, path: String?) {
                    // 압축 성공 시 호출
                    Log.d("video onSuccess", "$index $size $path")
                    onSuccess(path)
                }

                override fun onFailure(index: Int, failureMessage: String) {
                    // 압축 실패 시 호출
                    Log.d("video onFailure", "onFailure")
                }

                override fun onCancelled(index: Int) {
                    // 압축 취소 시 호출
                    Log.d("video onCancelled", "onCancelled")
                }
            },
        )
    }
}

fun formatDuration(durationMillis: Long): String {
    val minutes = (durationMillis / 1000) / 60
    val seconds = (durationMillis / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}

fun getVideoEncodingInfo(context: Context, videoFile: File) {
    val retriever = MediaMetadataRetriever()
    try {
        retriever.setDataSource(context, Uri.fromFile(videoFile))
        val mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        val bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)

        Log.d("VideoInfo", "MIME Type: $mimeType")
        Log.d("VideoInfo", "Duration: $duration ms")
        Log.d("VideoInfo", "Width: $width pixels")
        Log.d("VideoInfo", "Height: $height pixels")
        Log.d("VideoInfo", "Bitrate: $bitrate bps")
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        retriever.release()
    }
}

fun reencodeVideo(inputFile: File, outputFile: File, callback: (Boolean, String) -> Unit) {
    val command = arrayOf(
        "-i", inputFile.absolutePath,
        "-c:v", "libx264", // 비디오 코덱 설정
        "-b:v", "1000k",  // 비디오 비트레이트 설정
        "-c:a", "aac",    // 오디오 코덱 설정
        "-b:a", "192k",   // 오디오 비트레이트 설정
        "-movflags", "faststart", // 웹 스트리밍을 위해 메타데이터를 파일의 시작 부분에 배치
        outputFile.absolutePath
    )
    Config.setLogLevel(Config.getLogLevel())
    Config.enableLogCallback { logMessage ->
        Log.d("FFmpegLog", logMessage.text)
    }
    FFmpeg.executeAsync(command) { executionId, returnCode ->
        if (returnCode == Config.RETURN_CODE_SUCCESS) {
            callback(true, "Re-encoding successful")
        } else {
            callback(false, "Re-encoding failed with return code $returnCode")
        }
    }
}

fun uploadFileToS3PresignedUrl(presignedUrl: String, file: File, callback: (Boolean, String) -> Unit) {
    val client = OkHttpClient()
    val mediaType = "application/octet-stream".toMediaTypeOrNull()
    val requestBody = RequestBody.create(mediaType, file)

    val request = Request.Builder()
        .url(presignedUrl)
        .put(requestBody)
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            callback(false, "Upload failed: ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            if (response.isSuccessful) {
                callback(true, "Upload successful")
            } else {
                callback(false, "Upload failed: ${response.message}")
            }
        }
    })
}

fun extractAndUploadThumbnail(videoFile: File, thumbnailFile: File, presignedUrl: String, callback: (Boolean, String) -> Unit) {
    val extractCommand = arrayOf(
        "ffmpeg",
        "-i", videoFile.absolutePath,
        "-ss", "00:00:01.000",
        "-vframes", "1",
        thumbnailFile.absolutePath
    )

    try {
        val process = ProcessBuilder(*extractCommand).start()
        process.waitFor()
        if (process.exitValue() == 0) {
            val client = OkHttpClient()
            val mediaType = "image/jpeg".toMediaTypeOrNull()
            val requestBody = RequestBody.create(mediaType, thumbnailFile)

            val request = Request.Builder()
                .url(presignedUrl)
                .put(requestBody)
                .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    callback(false, "Upload failed: ${e.message}")
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (response.isSuccessful) {
                        callback(true, "Upload successful")
                    } else {
                        callback(false, "Upload failed: ${response.message}")
                    }
                }
            })
        } else {
            callback(false, "Thumbnail extraction failed with return code ${process.exitValue()}")
        }
    } catch (e: IOException) {
        e.printStackTrace()
        callback(false, "Thumbnail extraction failed with exception: ${e.message}")
    } catch (e: InterruptedException) {
        e.printStackTrace()
        callback(false, "Thumbnail extraction failed with exception: ${e.message}")
    }
}
