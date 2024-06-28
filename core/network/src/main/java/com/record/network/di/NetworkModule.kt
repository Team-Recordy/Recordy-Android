package com.record.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.record.common.buildconfig.BuildConfigFieldProvider
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

object NetworkModule {
    @Inject
    private lateinit var buildConfigFieldProvider: BuildConfigFieldProvider

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (buildConfigFieldProvider.get().isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    @NoneAuth
    fun provideNoneAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    @Auth
    fun provideAuthRetrofit(@Auth okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(buildConfigFieldProvider.get().baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @NoneAuth
    fun provideNoneAuthRetrofit(@NoneAuth okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(buildConfigFieldProvider.get().baseUrl)
        .client(okHttpClient)
        .build()
}