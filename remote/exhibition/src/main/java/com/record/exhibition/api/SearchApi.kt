package com.record.exhibition.api

import com.example.exhibition.model.remote.response.ResponseGetExhibitionSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/v1/search")
    suspend fun getExhibitionSearch(
        @Query("query") query: String,
    ): List<ResponseGetExhibitionSearchDto>
}
