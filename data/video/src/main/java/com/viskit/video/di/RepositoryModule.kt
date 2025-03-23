package com.viskit.video.di

import com.viskit.upload.repository.UploadRepository
import com.viskit.video.repository.UploadRepositoryImpl
import com.viskit.video.repository.UploadTaskImpl
import com.viskit.video.repository.VideoCoreRepository
import com.viskit.video.repository.VideoCoreRepositoryImpl
import com.viskit.video.repository.VideoRepository
import com.viskit.video.repository.VideoRepositoryImpl
import com.viskit.workmanager.upload.UploadTask
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
