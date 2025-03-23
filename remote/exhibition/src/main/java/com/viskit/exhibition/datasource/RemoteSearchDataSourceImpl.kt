package com.viskit.exhibition.datasource

import com.example.exhibition.model.remote.response.ResponseGetExhibitionSearchDto
import com.example.exhibition.model.remote.response.ResponseGetPlaceUsingMapDto
import com.example.exhibition.source.remote.RemoteSearchDataSource
import com.record.exhibition.api.SearchApi
import javax.inject.Inject

class RemoteSearchDataSourceImpl @Inject constructor(
    private val searchApi: SearchApi,
) : RemoteSearchDataSource {
    override suspend fun searchExhibition(query: String): List<ResponseGetExhibitionSearchDto> = searchApi.getExhibitionSearch(query)

    override suspend fun searchPlace(query: String): List<ResponseGetPlaceUsingMapDto> = searchApi.getSearchPlaceUsingMap(query)
}
