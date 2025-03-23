package com.viskit.exhibition.datasource

import com.example.exhibition.model.remote.request.RequestPatchExhibitionDto
import com.example.exhibition.model.remote.request.RequestPostExhibitionDto
import com.example.exhibition.source.remote.RemoteExhibitionDataSource
import com.viskit.exhibition.api.ExhibitionApi
import javax.inject.Inject

class RemoteExhibitionDataSourceImpl @Inject constructor(
    private val exhibitionApi: ExhibitionApi,
) : RemoteExhibitionDataSource {
    override suspend fun postExhibition(
        requestPostExhibitionDto: RequestPostExhibitionDto,
    ) = exhibitionApi.postExhibition(requestPostExhibitionDto)

    override suspend fun getExhibitionById(
        placeId: Int,
    ) = exhibitionApi.getExhibitionByPlaceId(placeId)

    override suspend fun getFreeExhibition(
        placeId: Int,
    ) = exhibitionApi.getFreeExhibitions(placeId)

    override suspend fun getClosingExhibition(
        placeId: Int,
    ) = exhibitionApi.getClosingExhibitions(placeId)

    override suspend fun patchExhibition(
        requestPatchExhibitionDto: RequestPatchExhibitionDto,
    ) = exhibitionApi.patchExhibition(requestPatchExhibitionDto)

    override suspend fun deleteExhibition(
        exhibitionId: Int,
    ) = exhibitionApi.deleteExhibitionById(exhibitionId)
}
