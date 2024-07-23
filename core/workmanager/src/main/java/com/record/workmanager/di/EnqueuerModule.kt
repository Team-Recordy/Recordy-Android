package com.record.workmanager.di

import com.record.workmanager.upload.UploadEnqueuer
import com.record.workmanager.upload.impl.UploadEnqueuerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EnqueuerModule {
    @Singleton
    @Binds
    abstract fun bindsUploadEnqueuer(uploadEnqueuerImpl: UploadEnqueuerImpl): UploadEnqueuer
}
