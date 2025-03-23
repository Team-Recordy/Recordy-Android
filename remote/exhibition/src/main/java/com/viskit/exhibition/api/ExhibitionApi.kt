package com.viskit.exhibition.api

import com.example.exhibition.model.remote.request.RequestPatchExhibitionDto
import com.example.exhibition.model.remote.request.RequestPostExhibitionDto
import com.example.exhibition.model.remote.response.ResponseGetExhibitionsDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExhibitionApi {
    @GET("/api/v1/exhibitions")
    suspend fun getExhibitionByPlaceId(
        @Query("placeId") placeId: Int,
    ): List<ResponseGetExhibitionsDto>

    @POST("/api/v1/exhibitions")
    suspend fun postExhibition(
        @Body requestPostExhibitionDto: RequestPostExhibitionDto,
    )

    @PATCH("/api/v1/exhibitions")
    suspend fun patchExhibition(
        @Body requestPatchExhibitionDto: RequestPatchExhibitionDto,
    )

    @GET("/api/v1/exhibitions/free")
    suspend fun getFreeExhibitions(
        @Query("placeId") placeId: Int,
    ): List<ResponseGetExhibitionsDto>

    @GET("/api/v1/exhibitions/closing")
    suspend fun getClosingExhibitions(
        @Query("placeId") placeId: Int,
    ): List<ResponseGetExhibitionsDto>

    @DELETE("/api/v1/exhibitions/{exhibitionId}")
    suspend fun deleteExhibitionById(
        @Path("exhibitionId") exhibitionId: Int,
    )
}
