package com.viskit.amplitude

import android.app.Application
import com.amplitude.android.Amplitude
import com.record.buildconfig.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AmplitudeModule {

    @Provides
    @Singleton
    fun provideAmplitude(application: Application): Amplitude {
        val configuration = com.amplitude.android.Configuration(
            apiKey = BuildConfig.AMPLITUDE_KEY,
            context = application.applicationContext
        )
        return Amplitude(configuration)
    }
}
