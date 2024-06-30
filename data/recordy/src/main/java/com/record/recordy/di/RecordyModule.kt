package com.record.recordy.di

import com.record.recordy.repository.RecordyRepository
import com.record.recordy.repository.RecordyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RecordyModule {
    @Binds
    @Singleton
    abstract fun bindsRecordyRepository(recordyRepositoryImpl: RecordyRepositoryImpl): RecordyRepository
}
