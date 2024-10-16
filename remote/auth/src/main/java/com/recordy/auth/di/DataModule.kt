package com.recordy.auth.di

import com.record.auth.source.remote.AuthRemoteDataSource
import com.recordy.auth.datasource.AuthRemoteDataSourceImpl
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
    abstract fun bindsAuthRemoteDataSource(authRemoteDataSource: AuthRemoteDataSourceImpl): AuthRemoteDataSource
}
