package com.record.datastore.token

import androidx.datastore.core.Serializer
import com.record.common.security.SecurityInterface
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class TokenDataSerializer @Inject constructor(
    private val securityManager: SecurityInterface,
) : Serializer<AuthToken> {
    private val securityKeyAlias = "data-store"
    override val defaultValue: AuthToken
        get() = AuthToken()

    override suspend fun readFrom(input: InputStream): AuthToken {
        val encryptedDataWithIv = input.readBytes()
        if (encryptedDataWithIv.size < 12) {
            return defaultValue
        }
        val iv = encryptedDataWithIv.copyOfRange(0, 12)
        val encryptedData = encryptedDataWithIv.copyOfRange(12, encryptedDataWithIv.size)
        val decryptedBytes = securityManager.decryptData(securityKeyAlias, encryptedData, iv)
        return try {
            Json.decodeFromString(
                deserializer = AuthToken.serializer(),
                string = decryptedBytes.decodeToString(),
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AuthToken, output: OutputStream) {
        securityManager.encryptData(
            securityKeyAlias,
            Json.encodeToString(
                serializer = AuthToken.serializer(),
                value = t,
            ),
        ).let {
            output.write(it.second + it.first)
        }
    }
}
