package com.viskit.exhibition.api

import com.example.exhibition.model.remote.request.RequestPostPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetPagingPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetReviewsDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceApi {
    @POST("/api/v1/places")
    suspend fun postPlace(
        @Body requestPostPlaceDto: RequestPostPlaceDto,
    )

    @GET("/api/v1/places/{id}")
    suspend fun getPlaceById(
        @Path("id") id: Int,
    ): ResponseGetPlaceDto

    @GET("/api/v1/places/{id}/reviews")
    suspend fun getReviewsById(
        @Path("id") id: Int,
    ): List<ResponseGetReviewsDto>

    @GET("/api/v1/places/exhibitions/geography")
    suspend fun getNearPlaces(
        @Query("number") number: Int,
        @Query("size") size: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("distance") distance: Double,
    ): ResponseGetPagingPlaceDto

    @GET("/api/v1/places/exhibitions/date")
    suspend fun getHasInProgressExhibitionPlaces(
        @Query("number") number: Int,
        @Query("size") size: Int,
    ): ResponseGetPagingPlaceDto
}
