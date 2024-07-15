package com.record.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.record.common.buildconfig.BuildConfigFieldProvider
import com.record.network.AuthenticationIntercept
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(buildConfigFieldProvider: BuildConfigFieldProvider): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (buildConfigFieldProvider.get().isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
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
    fun provideAuthRetrofit(@Auth okHttpClient: OkHttpClient, buildConfigFieldProvider: BuildConfigFieldProvider): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(buildConfigFieldProvider.get().baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @NoneAuth
    fun provideNoneAuthRetrofit(@NoneAuth okHttpClient: OkHttpClient, buildConfigFieldProvider: BuildConfigFieldProvider): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(buildConfigFieldProvider.get().baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideAuthInterceptor(interceptor: AuthenticationIntercept): Interceptor = interceptor
}
