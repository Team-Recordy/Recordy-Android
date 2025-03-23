package com.viskit.exhibition.datasource

import com.example.exhibition.model.remote.request.RequestPostPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetPagingPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetPlaceDto
import com.example.exhibition.model.remote.response.ResponseGetReviewsDto
import com.example.exhibition.source.remote.RemotePlaceDataSource
import com.viskit.exhibition.api.PlaceApi
import javax.inject.Inject

class RemotePlaceDataSourceImpl @Inject constructor(
    private val placeApi: PlaceApi,
) : RemotePlaceDataSource {
    override suspend fun postPlace(requestPostPlaceDto: RequestPostPlaceDto) =
        placeApi.postPlace(requestPostPlaceDto)

    override suspend fun getPlaceById(id: Int): ResponseGetPlaceDto =
        placeApi.getPlaceById(id)

    override suspend fun getReviewsById(id: Int): List<ResponseGetReviewsDto> =
        placeApi.getReviewsById(id)

    override suspend fun getNearPlace(number: Int, size: Int, latitude: Double, longitude: Double, distance: Double): ResponseGetPagingPlaceDto =
        placeApi.getNearPlaces(number, size, latitude, longitude, distance)

    override suspend fun getHasInProgressExhibitionPlaces(number: Int, size: Int): ResponseGetPagingPlaceDto =
        placeApi.getHasInProgressExhibitionPlaces(number, size)
}
