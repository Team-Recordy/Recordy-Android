package com.viskit.exhibition.di

import com.example.exhibition.source.remote.RemoteExhibitionDataSource
import com.example.exhibition.source.remote.RemotePlaceDataSource
import com.example.exhibition.source.remote.RemoteSearchDataSource
import com.record.exhibition.datasource.RemoteExhibitionDataSourceImpl
import com.record.exhibition.datasource.RemotePlaceDataSourceImpl
import com.record.exhibition.datasource.RemoteSearchDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindsRemoteExhibitionDataSource(remoteExhibitionDataSourceImpl: RemoteExhibitionDataSourceImpl): RemoteExhibitionDataSource

    @Binds
    @Singleton
    abstract fun bindsRemotePlaceDataSource(remotePlaceDataSourceImpl: RemotePlaceDataSourceImpl): RemotePlaceDataSource

    @Binds
    @Singleton
    abstract fun bindsRemoteSearchDataSource(remoteSearchDataSourceImpl: RemoteSearchDataSourceImpl): RemoteSearchDataSource
}
