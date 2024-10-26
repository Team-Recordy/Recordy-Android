package com.record.exhibition.di

import com.example.exhibition.source.remote.RemoteExhibitionDataSource
import com.record.exhibition.datasource.RemoteExhibitionDataSourceImpl
import com.record.exhibition.datasource.RemotePlaceDataSourceImpl
import com.example.exhibition.source.remote.RemotePlaceDataSource
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
}
