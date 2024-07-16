package com.record.keyword.source

interface RemoteKeywordDataSource {
    fun getKeywords(): List<String>
}
