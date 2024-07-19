package com.record.user.di

import com.record.user.datasource.RemoteUserDataSourceImpl
import com.record.user.source.remote.RemoteUserDataSource
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
    abstract fun bindsRemoteUserDataSource(remoteUserDataSourceImpl: RemoteUserDataSourceImpl): RemoteUserDataSource
}
