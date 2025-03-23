package com.viskit.keyword.repository

import com.viskit.keyword.model.PreferenceKeyword

interface KeywordRepository {
    suspend fun getKeywords(): Result<PreferenceKeyword>
}
