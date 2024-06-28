package org.sopt.common.security

interface SecurityInterface {
    fun encryptData(keyAlias: String, text: String): Pair<ByteArray, ByteArray>

    fun decryptData(keyAlias: String, encryptedData: ByteArray, iv: ByteArray): ByteArray
}