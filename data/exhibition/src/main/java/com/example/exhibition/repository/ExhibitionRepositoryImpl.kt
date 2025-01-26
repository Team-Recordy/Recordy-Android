package com.example.exhibition.repository

import com.example.exhibition.model.remote.request.RequestPostPlaceDto
import com.example.exhibition.model.remote.response.toDomain
import com.example.exhibition.source.remote.RemoteExhibitionDataSource
import com.example.exhibition.source.remote.RemotePlaceDataSource
import com.record.exhibition.model.Exhibition
import com.record.exhibition.model.ExhibitionFilter
import com.record.exhibition.model.Place
import com.record.exhibition.model.PlaceUsingMap
import com.record.exhibition.repository.ExhibitionRepository
import com.record.model.Cursor
import com.record.model.Page
import com.record.model.exception.ApiError
import com.record.video.model.toCore
import com.record.video.repository.VideoRepository
import retrofit2.HttpException
import javax.inject.Inject

class ExhibitionRepositoryImpl @Inject constructor(
    private val remoteExhibitionDataSource: RemoteExhibitionDataSource,
    private val remotePlaceDataSource: RemotePlaceDataSource,
    private val videoRepository: VideoRepository,
) : ExhibitionRepository {
    override suspend fun postPlace(usingMap: PlaceUsingMap): Result<Unit> = runCatching {
        remotePlaceDataSource.postPlace(
            RequestPostPlaceDto(
                address = usingMap.address,
                name = usingMap.name,
                latitude = usingMap.latitude,
                longitude = usingMap.longitude,
                id = usingMap.platformPlaceId,
            ),
        )
    }
    override suspend fun getNearPlaceData(number: Int, size: Int, latitude: Double, longitude: Double) =
        runCatching {
            remotePlaceDataSource.getNearPlace(number = number, size = size, latitude = if (latitude < 0) -latitude else latitude, longitude = if (longitude < 0) -longitude else longitude, distance = 3000000.0)
        }.mapCatching { it ->
            Page(
                hasNext = it.hasNext,
                page = it.pageNumber,
                data = it.content.map { placeDto ->
                    runCatching {
                        val videoResult = if (placeDto.recordSize != 0) videoRepository.getPlaceVideos(placeDto.id, 0, placeDto.recordSize).getOrNull() else Cursor(hasNext = false, nextCursor = null, data = emptyList())
                        Place(
                            placeId = placeDto.id,
                            address = placeDto.address ?: "",
                            name = placeDto.name,
                            exhibitionCount = placeDto.exhibitionSize,
                            recordCount = placeDto.recordSize,
                            exhibitionRecord = videoResult?.data?.map { it.toCore() } ?: emptyList(),
                            platformId = placeDto.platformId?.toLong() ?: 0,
                        )
                    }.getOrDefault(
                        Place(
                            placeId = placeDto.id,
                            address = placeDto.address ?: "",
                            name = placeDto.name,
                            exhibitionCount = placeDto.exhibitionSize,
                            recordCount = placeDto.recordSize,
                            exhibitionRecord = emptyList(),
                            platformId = placeDto.platformId?.toLong() ?: 0,
                        ),
                    )
                },
            )
        }.recoverCatching { exception ->
            when (exception) {
                is HttpException -> {
                    throw ApiError(exception.message())
                }

                else -> {
                    throw exception
                }
            }
        }

    override suspend fun getPlaceById(placeId: Long): Result<Place> = runCatching {
        remotePlaceDataSource.getPlaceById(placeId.toInt())
    }.mapCatching { it ->
        val result = videoRepository.getPlaceVideos(it.id, 0, it.recordSize).getOrNull()
        Place(
            placeId = it.id,
            address = it.address ?: "",
            name = it.name,
            exhibitionCount = it.exhibitionSize,
            recordCount = it.recordSize,
            exhibitionRecord = result?.data?.map { it.toCore() },
            platformId = it.platformId?.toLong() ?: 0,
        )
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }

    override suspend fun getExhibitions(placeId: Long, filter: ExhibitionFilter): Result<List<Exhibition>> = runCatching {
        when (filter) {
            ExhibitionFilter.DEFAULT -> remoteExhibitionDataSource.getExhibitionById(placeId.toInt())
            ExhibitionFilter.FREE -> remoteExhibitionDataSource.getFreeExhibition(placeId.toInt())
            ExhibitionFilter.CLOSING -> remoteExhibitionDataSource.getClosingExhibition(placeId.toInt())
        }
    }.mapCatching {
        it.map { it.toDomain() }
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }

    override suspend fun getExhibitionsFromDate(number: Int, size: Int): Result<Page<Place>> = runCatching {
        remotePlaceDataSource.getHasInProgressExhibitionPlaces(number = number, size = size)
    }.mapCatching { it ->
        Page(
            hasNext = it.hasNext,
            page = it.pageNumber,
            data = it.content.map { placeDto ->
                runCatching {
                    val videoResult = if (placeDto.recordSize != 0) videoRepository.getPlaceVideos(placeDto.id, 0, placeDto.recordSize).getOrNull() else Cursor(hasNext = false, nextCursor = null, data = emptyList())
                    Place(
                        placeId = placeDto.id,
                        address = placeDto.address ?: "",
                        name = placeDto.name,
                        exhibitionCount = placeDto.exhibitionSize,
                        recordCount = placeDto.recordSize,
                        exhibitionRecord = videoResult?.data?.map { it.toCore() } ?: emptyList(),
                        platformId = placeDto.platformId?.toLong() ?: 0,
                    )
                }.getOrDefault(
                    Place(
                        placeId = placeDto.id,
                        address = placeDto.address ?: "",
                        name = placeDto.name,
                        exhibitionCount = placeDto.exhibitionSize,
                        recordCount = placeDto.recordSize,
                        exhibitionRecord = emptyList(),
                        platformId = placeDto.platformId?.toLong() ?: 0,
                    ),
                )
            },
        )
    }.recoverCatching { exception ->
        when (exception) {
            is HttpException -> {
                throw ApiError(exception.message())
            }

            else -> {
                throw exception
            }
        }
    }
}
