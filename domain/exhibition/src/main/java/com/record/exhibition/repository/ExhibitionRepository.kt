package com.record.exhibition.repository

import com.record.exhibition.model.Exhibition
import com.record.exhibition.model.ExhibitionFilter
import com.record.exhibition.model.Place
import com.record.exhibition.model.PlaceUsingMap
import com.record.model.Page

interface ExhibitionRepository {
    suspend fun postPlace(usingMap: PlaceUsingMap) :Result<Unit>
    suspend fun getNearPlaceData(number: Int, size: Int, latitude: Double, longitude: Double): Result<Page<Place>>
    suspend fun getPlaceById(placeId: Long): Result<Place>
    suspend fun getExhibitions(placeId: Long, filter: ExhibitionFilter): Result<List<Exhibition>>
}
