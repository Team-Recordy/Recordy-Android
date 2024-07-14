package com.recordy.auth.di

import com.recordy.auth.datasource.AuthLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthLocalDataModule {
    @Binds
    @Singleton
    abstract fun bindsAuthLocalDataSource(authLocalDataSource: AuthLocalDataSourceImpl): AuthLocalDataSourceImpl
}

