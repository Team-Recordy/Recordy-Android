package com.record.video.source.local

interface LocalUserInfoDataSource {
    suspend fun getMyId(): Long
}
