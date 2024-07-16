package com.record.keyword.di

import com.record.keyword.repository.KeywordRepository
import com.record.keyword.repository.KeywordRepositoryImpl
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
