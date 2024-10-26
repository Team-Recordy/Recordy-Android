package com.example.exhibition.source.remote

import com.example.exhibition.model.remote.request.RequestPatchExhibitionDto
import com.example.exhibition.model.remote.request.RequestPostExhibitionDto
import com.example.exhibition.model.remote.response.ResponseGetExhibitionsDto

interface RemoteExhibitionDataSource {
    suspend fun postExhibition(
        requestPostExhibitionDto: RequestPostExhibitionDto
    )

    suspend fun getExhibitionById(
        placeId: Int
    ): List<ResponseGetExhibitionsDto>

    suspend fun getFreeExhibition(
        placeId: Int
    ): List<ResponseGetExhibitionsDto>

    suspend fun getClosingExhibition(
        placeId: Int
    ): List<ResponseGetExhibitionsDto>

    suspend fun patchExhibition(
        requestPatchExhibitionDto: RequestPatchExhibitionDto
    )

    suspend fun deleteExhibition(
        exhibitionId: Int
    )
}
