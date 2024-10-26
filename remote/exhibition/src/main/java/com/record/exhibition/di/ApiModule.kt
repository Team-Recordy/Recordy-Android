package com.record.exhibition.di

import com.record.exhibition.api.ExhibitionApi
import com.record.exhibition.api.PlaceApi
import com.record.network.di.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Provides
    @Singleton
    fun providesExhibitionApi(@Auth retrofit: Retrofit): ExhibitionApi = retrofit.create()

    @Provides
    @Singleton
    fun providesPlaceApi(@Auth retrofit: Retrofit): PlaceApi = retrofit.create()
}
