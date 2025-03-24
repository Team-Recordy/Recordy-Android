package com.viskit.security.di

import com.viskit.common.security.CryptoManager
import com.viskit.security.CryptoManagerImpl
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
