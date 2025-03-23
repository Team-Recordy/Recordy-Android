package com.viskit.user.di

import com.viskit.user.datasource.RemoteUserDataSourceImpl
import com.viskit.user.source.remote.RemoteUserDataSource
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
