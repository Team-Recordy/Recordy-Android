package com.record.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.common.security.SecurityInterface
import org.sopt.common.security.SecurityUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {
    @Singleton
    @Binds
    abstract fun bindsSecurityUtil(securityUtil: SecurityUtil): SecurityInterface
}