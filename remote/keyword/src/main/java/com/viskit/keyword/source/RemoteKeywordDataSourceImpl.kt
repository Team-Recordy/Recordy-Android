package com.viskit.keyword.source

import com.viskit.keyword.api.KeywordApi
import javax.inject.Inject

class RemoteKeywordDataSourceImpl @Inject constructor(
    private val keywordApi: KeywordApi,
) : RemoteKeywordDataSource {
    override suspend fun getKeywords(): List<String> = keywordApi.getKeywords()
}
