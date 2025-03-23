package com.viskit.keyword.di

import com.viskit.keyword.repository.KeywordRepository
import com.viskit.keyword.repository.KeywordRepositoryImpl
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
    abstract fun bindsKeywordRepository(keywordRepositoryImpl: KeywordRepositoryImpl): KeywordRepository
}
