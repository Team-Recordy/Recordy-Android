package com.record.recordy.di

import com.record.recordy.datasource.RecordyRemoteDataSourceImpl
import com.record.recordy.source.remote.RecordyRemoteDataSource
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
    abstract fun bindsRecordyRemoteDataSource(recordyRemoteDataSourceImpl: RecordyRemoteDataSourceImpl): RecordyRemoteDataSource
}
