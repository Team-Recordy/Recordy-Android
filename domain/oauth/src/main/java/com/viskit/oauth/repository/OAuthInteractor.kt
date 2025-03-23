package com.viskit.oauth.repository

import com.viskit.oauth.model.KakaoToken

interface OAuthInteractor {
    suspend fun loginByKakao(): Result<KakaoToken>
    fun logout()
    fun withdraw()
}
