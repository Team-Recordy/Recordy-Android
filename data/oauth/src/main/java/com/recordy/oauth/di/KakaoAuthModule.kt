package com.recordy.oauth.di

import com.recordy.oauth.repository.OAuthInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class KakaoAuthModule {
    @Binds
    @ActivityScoped
    abstract fun provideKakaoAuthRepository(
        kakaoAuthInteractor: com.recordy.oauth.repository.KakaoAuthManager,
    ): OAuthInteractor
}
