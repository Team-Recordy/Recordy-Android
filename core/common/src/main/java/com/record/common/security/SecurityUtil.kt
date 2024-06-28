package org.sopt.common.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class SecurityUtil @Inject constructor() : SecurityInterface {
    private val provider = "AndroidKeyStore"
    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }
    private val charset by lazy {
        charset("UTF-8")

    }
    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES, provider)
    }

    override fun encryptData(keyAlias: String, text: String): Pair<ByteArray, ByteArray> {
        val secretKey = getOrCreateSecretKey(keyAlias)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(text.toByteArray(charset))
        return Pair(encryptedData, iv)
    }

    override fun decryptData(keyAlias: String, encryptedData: ByteArray, iv: ByteArray): ByteArray {
        val secretKey = getSecretKey(keyAlias)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        return cipher.doFinal(encryptedData)
    }

    private fun getOrCreateSecretKey(keyAlias: String): SecretKey {
        if (!keyStore.containsAlias(keyAlias)) {
            return generateSecretKey(keyAlias)
        }
        return getSecretKey(keyAlias)
    }
    private fun generateSecretKey(keyAlias: String): SecretKey {
        val parameterSpec = KeyGenParameterSpec.Builder(
            keyAlias,
            PURPOSE_ENCRYPT or PURPOSE_DECRYPT
        ).run {
            setBlockModes(BLOCK_MODE_GCM)
            setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
            build()
        }
        keyGenerator.init(parameterSpec)
        return keyGenerator.generateKey()
    }

    private fun getSecretKey(keyAlias: String) =
        (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
}