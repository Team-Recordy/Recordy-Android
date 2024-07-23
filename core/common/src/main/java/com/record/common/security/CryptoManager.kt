package com.record.common.security

interface CryptoManager {
    fun encryptData(keyAlias: String, text: String): Pair<ByteArray, ByteArray>

    fun decryptData(keyAlias: String, encryptedData: ByteArray, iv: ByteArray): ByteArray
}
