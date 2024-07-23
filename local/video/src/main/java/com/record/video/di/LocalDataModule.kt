package com.record.video.di

import com.record.video.datasource.LocalVideoDataSourceImpl
import com.record.video.source.local.LocalVideoDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataModule {
    @Binds
    @Singleton
    abstract fun bindsLocalVideoDataSource(localVideoDataSourceImpl: LocalVideoDataSourceImpl): LocalVideoDataSource
}
