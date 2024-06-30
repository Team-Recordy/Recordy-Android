package com.record.buildconfig.di

import com.record.buildconfig.impl.BuildConfigFieldsProviderImpl
import com.record.common.buildconfig.BuildConfigFieldProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object BuildConfigModule {
    @Provides
    @Singleton
    fun provideBuildConfigFieldsProvider(
        buildConfigFieldProvider: BuildConfigFieldsProviderImpl
    ): BuildConfigFieldProvider = buildConfigFieldProvider
}
