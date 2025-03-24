package com.viskit.login

import com.viskit.oauth.repository.OAuthInteractor
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface OAuthEntryPoint {
    fun getOAuthInteractor(): OAuthInteractor
}
