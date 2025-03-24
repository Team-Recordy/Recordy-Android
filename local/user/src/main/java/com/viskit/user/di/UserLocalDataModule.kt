package com.viskit.user.di

import com.viskit.user.datasource.UserLocalDataSourceImpl
import com.viskit.user.source.local.UserLocalDataSource
import com.viskit.video.source.local.LocalUserInfoDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserLocalDataModule {
    @Binds
    @Singleton
    abstract fun bindsUserLocalDataModule(userLocalDataSource: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsLocalUserInfoDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): LocalUserInfoDataSource
}
