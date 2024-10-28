package com.example.exhibition.source.remote

import com.example.exhibition.model.remote.request.RequestPostPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetPagingPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetReviewsDto

interface RemotePlaceDataSource {
    suspend fun postPlace(
        requestPostPlaceDto: RequestPostPlaceDto,
    )

    suspend fun getPlaceById(
        id: Int,
    ): ResponseGetPlaceDto

    suspend fun getReviewsById(
        id: Int,
    ): List<ResponseGetReviewsDto>

    suspend fun getNearPlace(
        number: Int,
        size: Int,
        latitude: Double,
        longitude: Double,
        distance: Double,
    ): ResponseGetPagingPlaceDto

    suspend fun getHasInProgressExhibitionPlaces(
        number: Int,
        size: Int,
    ): List<ResponseGetPlaceDto>
}
