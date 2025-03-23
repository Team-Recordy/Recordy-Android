package com.viskit.user.di

import com.viskit.network.di.Auth
import com.viskit.user.api.UserApi
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
