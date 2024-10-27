package com.example.exhibition.source.remote

import com.example.exhibition.model.remote.response.ResponseGetExhibitionSearchDto
import com.example.exhibition.model.remote.response.ResponseGetPlaceUsingMapDto

interface RemoteSearchDataSource {
    suspend fun searchExhibition(query: String): List<ResponseGetExhibitionSearchDto>
    suspend fun searchPlace(query: String):List<ResponseGetPlaceUsingMapDto>
}
