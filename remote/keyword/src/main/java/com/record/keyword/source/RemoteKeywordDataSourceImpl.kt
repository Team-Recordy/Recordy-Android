package com.record.keyword.source

import com.record.keyword.api.KeywordApi
import javax.inject.Inject

class RemoteKeywordDataSourceImpl @Inject constructor(
    private val keywordApi: KeywordApi,
) : RemoteKeywordDataSource {
    override fun getKeywords(): List<String> = keywordApi.getKeywords()
}
