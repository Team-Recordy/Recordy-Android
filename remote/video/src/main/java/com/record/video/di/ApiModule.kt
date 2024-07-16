package com.record.video.di

import com.record.network.di.Auth
import com.record.video.api.UploadApi
import com.record.video.api.VideoApi
import com.record.video.api.VideoCoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Provides
    @Singleton
    fun providesUploadApi(@Auth retrofit: Retrofit): UploadApi = retrofit.create()

    @Provides
    @Singleton
    fun providesVideoApi(@Auth retrofit: Retrofit): VideoApi = retrofit.create()

    @Provides
    @Singleton
    fun providesVideoCoreApi(@Auth retrofit: Retrofit): VideoCoreApi = retrofit.create()
}
