package com.record.video.api

import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface VideoCoreApi {
    @POST("/api/v1/records/{record_id}")
    fun postWatchVideo(
        @Path("record_id") recordId: Long,
    )

    @DELETE("/api/v1/records/{record_id}")
    fun deleteVideo(
        @Path("record_id") recordId: Long,
    )
}
