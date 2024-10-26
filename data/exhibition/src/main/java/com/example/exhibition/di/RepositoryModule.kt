package com.example.exhibition.di

import com.example.exhibition.repository.ExhibitionRepositoryImpl
import com.record.exhibition.repository.ExhibitionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsExhibitionRepository(exhibitionRepositoryImpl: ExhibitionRepositoryImpl): ExhibitionRepository
}
