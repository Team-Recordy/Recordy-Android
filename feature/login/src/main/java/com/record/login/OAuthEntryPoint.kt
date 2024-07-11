package com.record.login

import com.recordy.oauth.repository.OAuthInteractor
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface OAuthEntryPoint {
    fun getOAuthInteractor(): OAuthInteractor
}
