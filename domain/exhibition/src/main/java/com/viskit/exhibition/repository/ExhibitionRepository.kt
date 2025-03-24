package com.viskit.exhibition.repository

import com.viskit.exhibition.model.Exhibition
import com.viskit.exhibition.model.ExhibitionFilter
import com.viskit.exhibition.model.Place
import com.viskit.exhibition.model.PlaceUsingMap
import com.viskit.model.Page

interface ExhibitionRepository {
    suspend fun postPlace(usingMap: PlaceUsingMap): Result<Unit>
    suspend fun getNearPlaceData(number: Int, size: Int, latitude: Double, longitude: Double): Result<Page<Place>>
    suspend fun getPlaceById(placeId: Long): Result<Place>
    suspend fun getExhibitions(placeId: Long, filter: ExhibitionFilter): Result<List<Exhibition>>
    suspend fun getExhibitionsFromDate(number: Int, size: Int): Result<Page<Place>>
}
