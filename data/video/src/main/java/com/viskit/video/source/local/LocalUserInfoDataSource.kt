package com.viskit.video.source.local

interface LocalUserInfoDataSource {
    suspend fun getMyId(): Long
}
