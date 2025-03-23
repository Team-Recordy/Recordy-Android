package com.viskit.keyword.source

interface RemoteKeywordDataSource {
    suspend fun getKeywords(): List<String>
}
