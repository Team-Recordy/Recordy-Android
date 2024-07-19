package com.record.keyword.repository

import com.record.keyword.model.PreferenceKeyword

interface KeywordRepository {
    suspend fun getKeywords(): Result<PreferenceKeyword>
}
