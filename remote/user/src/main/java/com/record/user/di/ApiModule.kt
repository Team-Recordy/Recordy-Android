package com.record.user.di

import com.record.network.di.Auth
import com.record.user.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun providesUserApi(@Auth retrofit: Retrofit): UserApi = retrofit.create()
}
