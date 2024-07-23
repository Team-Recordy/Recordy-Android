package com.record.video.datasource

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.os.bundleOf
import com.record.video.model.local.LocalVideoInfo
import com.record.video.source.local.LocalVideoDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalVideoDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocalVideoDataSource {
    private val uriExternal: Uri by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL,
            )
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }
    }

    private val projection = arrayOf(
        MediaStore.Video.VideoColumns.DATA,
        MediaStore.Video.VideoColumns.DISPLAY_NAME,
        MediaStore.Video.VideoColumns.SIZE,
        MediaStore.Video.VideoColumns.DATE_TAKEN,
        MediaStore.Video.VideoColumns.DATE_ADDED,
        MediaStore.Video.VideoColumns._ID,
    )

    private val sortedOrder = "${MediaStore.Video.VideoColumns.DATE_ADDED} DESC"

    private val contentResolver by lazy {
        context.contentResolver
    }

    override suspend fun getVideosFromGallery(
        page: Int,
        loadSize: Int,
        currentLocation: String?,
    ): MutableList<LocalVideoInfo> = withContext(Dispatchers.IO) {
        val localVideoList = mutableListOf<LocalVideoInfo>()
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        if (currentLocation != null) {
            selection = "${MediaStore.Video.Media.DATA} LIKE ?"
            selectionArgs = arrayOf("$currentLocation")
        }
        val limit = loadSize
        val offset = (page - 1) * loadSize
        val queryStartTime = System.currentTimeMillis()
        val query = getQuery(offset, limit, selection, selectionArgs)
        val queryEndTime = System.currentTimeMillis()
        Log.d("getAllVideos", "Query time: ${queryEndTime - queryStartTime} ms")
        query?.use { cursor ->
            val processingStartTime = System.currentTimeMillis()
            val videoMetadataDeferred = mutableListOf<Deferred<LocalVideoInfo>>()
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID))
                val filepath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME))
                val size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.SIZE))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_TAKEN))
                val contentUri = ContentUris.withAppendedId(uriExternal, id)

                val videoMetadata = async {
                    val duration = getVideoDuration(context, contentUri)
                    LocalVideoInfo(
                        id,
                        filepath = filepath,
                        uri = contentUri.toString(),
                        name = name,
                        date = date ?: "",
                        size = size,
                        duration = duration,
                    )
                }
                videoMetadataDeferred.add(videoMetadata)
            }
            localVideoList.addAll(videoMetadataDeferred.awaitAll())
            val processingEndTime = System.currentTimeMillis()
            Log.d("getAllVideos", "Processing time: ${processingEndTime - processingStartTime} ms")
        }
        return@withContext localVideoList
    }

    fun getQuery(
        offset: Int,
        limit: Int,
        selection: String?,
        selectionArgs: Array<String>?,
    ) = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        val bundle = bundleOf(
            ContentResolver.QUERY_ARG_OFFSET to offset,
            ContentResolver.QUERY_ARG_LIMIT to limit,
            ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
            ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
            ContentResolver.QUERY_ARG_SQL_SELECTION to selection,
            ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to selectionArgs,
        )
        contentResolver.query(uriExternal, projection, bundle, null)
    } else {
        contentResolver.query(
            uriExternal,
            projection,
            selection,
            selectionArgs,
            "$sortedOrder DESC LIMIT $limit OFFSET $offset",
        )
    }

    fun getVideoDuration(context: Context, uri: Uri): Long {
        val retriever = MediaMetadataRetriever()
        val startTime = System.currentTimeMillis()
        val duration = try {
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
        val endTime = System.currentTimeMillis()
        Log.d("getVideoDuration", "Duration extraction time: ${endTime - startTime} ms")
        return duration
    }
}
