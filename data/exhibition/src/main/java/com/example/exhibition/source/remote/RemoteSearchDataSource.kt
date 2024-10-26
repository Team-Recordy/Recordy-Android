package com.example.exhibition.source.remote

import com.example.exhibition.model.remote.response.ResponseGetExhibitionSearchDto

interface RemoteSearchDataSource {
    suspend fun searchExhibition(query: String): List<ResponseGetExhibitionSearchDto>
}
