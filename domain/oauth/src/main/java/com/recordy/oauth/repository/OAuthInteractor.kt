package com.recordy.oauth.repository

import com.recordy.oauth.model.KakaoToken

interface OAuthInteractor {
    suspend fun loginByKakao(): Result<KakaoToken>
    fun logout()
    fun withdraw()
}
