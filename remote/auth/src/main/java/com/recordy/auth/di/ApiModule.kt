package com.recordy.auth.di

import com.record.network.di.Auth
import com.recordy.auth.api.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesRecordyApi(@Auth retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
