package com.record.keyword.source

interface RemoteKeywordDataSource {
    suspend fun getKeywords(): List<String>
}
