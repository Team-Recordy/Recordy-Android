package com.viskit.video.di

import com.viskit.network.di.Auth
import com.viskit.network.di.S3
import com.viskit.video.api.BucketApi
import com.viskit.video.api.UploadApi
import com.viskit.video.api.VideoApi
import com.viskit.video.api.VideoCoreApi
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

    @Provides
    @Singleton
    fun providesS3BucketApi(@S3 retrofit: Retrofit): BucketApi = retrofit.create()
}
