package com.viskit.exhibition.di

import com.viskit.exhibition.api.ExhibitionApi
import com.viskit.exhibition.api.PlaceApi
import com.viskit.exhibition.api.SearchApi
import com.viskit.network.di.Auth
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

    @Provides
    @Singleton
    fun providesSearchApi(@Auth retrofit: Retrofit): SearchApi = retrofit.create()
}
