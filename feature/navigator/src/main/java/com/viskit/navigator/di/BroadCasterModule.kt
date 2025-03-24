package com.viskit.navigator.di

import com.viskit.common.intentprovider.UploadBroadCaster
import com.viskit.navigator.broadcastreceiver.UploadBroadCasterImpl
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
