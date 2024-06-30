package com.record.recordy.di

import com.record.network.di.Auth
import com.record.recordy.api.RecordyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesRecordyApi(@Auth retrofit: Retrofit): RecordyApi =
        retrofit.create(RecordyApi::class.java)
}
