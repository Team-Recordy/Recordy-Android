package com.record.keyword.repository

import com.record.keyword.model.PreferenceKeyword

interface KeywordRepository {
    fun getKeywords(): Result<PreferenceKeyword>
}
