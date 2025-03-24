package com.viskit.exhibition.api

import com.example.exhibition.model.remote.response.ResponseGetExhibitionSearchDto
import com.example.exhibition.model.remote.response.ResponseGetPlaceUsingMapDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/v1/search")
    suspend fun getExhibitionSearch(
        @Query("query") query: String,
    ): List<ResponseGetExhibitionSearchDto>

    @GET("/api/v1/places/platform/search")
    suspend fun getSearchPlaceUsingMap(
        @Query("query") query: String,
    ): List<ResponseGetPlaceUsingMapDto>
}
