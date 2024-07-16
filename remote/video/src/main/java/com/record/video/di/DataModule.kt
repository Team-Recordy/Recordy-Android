package com.record.video.di

import com.record.video.datasource.RemoteUploadDataSourceImpl
import com.record.video.datasource.RemoteVideoCoreDataSourceImpl
import com.record.video.datasource.RemoteVideoDataSourceImpl
import com.record.video.source.remote.RemoteUploadDataSource
import com.record.video.source.remote.RemoteVideoCoreDataSource
import com.record.video.source.remote.RemoteVideoDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindsRemoteUploadDataSource(remoteUploadDataSourceImpl: RemoteUploadDataSourceImpl): RemoteUploadDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteVideoCoreDataSource(remoteVideoCoreDataSourceImpl: RemoteVideoCoreDataSourceImpl): RemoteVideoCoreDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteVideoDataSource(remoteVideoDataSourceImpl: RemoteVideoDataSourceImpl): RemoteVideoDataSource
}
