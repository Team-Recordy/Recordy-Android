package com.viskit.auth.di

import com.viskit.auth.source.local.AuthLocalDataSource
import com.viskit.auth.datasource.AuthLocalDataSourceImpl
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
    abstract fun bindsAuthLocalDataSource(authLocalDataSource: AuthLocalDataSourceImpl): AuthLocalDataSource
}
