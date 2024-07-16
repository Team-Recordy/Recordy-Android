package com.record.keyword.di

import com.record.keyword.source.RemoteKeywordDataSource
import com.record.keyword.source.RemoteKeywordDataSourceImpl
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
