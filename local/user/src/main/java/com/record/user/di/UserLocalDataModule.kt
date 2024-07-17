package com.record.user.di

import com.record.user.datasource.UserLocalDataSourceImpl
import com.record.user.source.local.UserLocalDataSource
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
}
