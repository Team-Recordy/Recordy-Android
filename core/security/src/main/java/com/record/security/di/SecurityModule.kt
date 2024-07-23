package com.record.security.di

import com.record.common.security.CryptoManager
import com.record.security.CryptoManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {
    @Singleton
    @Binds
    abstract fun bindsCryptoManager(cryptoManagerImpl: CryptoManagerImpl): CryptoManager
}
