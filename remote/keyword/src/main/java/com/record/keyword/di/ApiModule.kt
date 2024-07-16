package com.record.keyword.di

import com.record.keyword.api.KeywordApi
import com.record.network.di.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn
object ApiModule {
    @Provides
    @Singleton
    fun providesKeywordApi(@Auth retrofit: Retrofit): KeywordApi = retrofit.create()
}
