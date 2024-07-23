package com.record.navigator.di

import com.record.common.intentprovider.UploadBroadCaster
import com.record.navigator.broadcastreceiver.UploadBroadCasterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BroadCasterModule {
    @Binds
    @Singleton
    abstract fun bindsUploadBroadCaster(uploadBroadCasterImpl: UploadBroadCasterImpl): UploadBroadCaster
}
