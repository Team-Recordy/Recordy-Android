package com.record.keyword.api

import retrofit2.http.GET

interface KeywordApi {
    @GET("/api/v1/keywords")
    suspend fun getKeywords(): List<String>
}
