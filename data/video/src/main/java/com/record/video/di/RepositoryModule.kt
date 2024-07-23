package com.record.video.di

import com.record.upload.repository.UploadRepository
import com.record.video.repository.UploadRepositoryImpl
import com.record.video.repository.UploadTaskImpl
import com.record.video.repository.VideoCoreRepository
import com.record.video.repository.VideoCoreRepositoryImpl
import com.record.video.repository.VideoRepository
import com.record.video.repository.VideoRepositoryImpl
import com.record.workmanager.upload.UploadTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsUploadRepository(uploadRepositoryImpl: UploadRepositoryImpl): UploadRepository

    @Binds
    @Singleton
    abstract fun bindsVideoCoreRepository(videoCoreRepositoryImpl: VideoCoreRepositoryImpl): VideoCoreRepository

    @Binds
    @Singleton
    abstract fun bindsVideoRepository(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository

    @Binds
    @Singleton
    abstract fun bindsUploadTask(uploadTaskImpl: UploadTaskImpl): UploadTask
}
