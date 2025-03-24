package com.viskit.keyword.di

import com.viskit.keyword.source.RemoteKeywordDataSource
import com.viskit.keyword.source.RemoteKeywordDataSourceImpl
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
    abstract fun bindsKeywordDataSource(remoteKeywordDataSourceImpl: RemoteKeywordDataSourceImpl): RemoteKeywordDataSource
}
