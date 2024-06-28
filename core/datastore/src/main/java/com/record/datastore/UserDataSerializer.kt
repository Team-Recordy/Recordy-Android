package org.sopt.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.sopt.common.security.SecurityInterface
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserDataSerializer @Inject constructor(
    private val securityManager: SecurityInterface,
) : Serializer<UserData> {
    private val securityKeyAlias = "data-store"
    override val defaultValue: UserData
        get() = UserData()

    override suspend fun readFrom(input: InputStream): UserData {
        val encryptedDataWithIv = input.readBytes()
        if (encryptedDataWithIv.size < 12) {
            return defaultValue
        }
        val iv = encryptedDataWithIv.copyOfRange(0, 12)
        val encryptedData = encryptedDataWithIv.copyOfRange(12, encryptedDataWithIv.size)
        val decryptedBytes = securityManager.decryptData(securityKeyAlias, encryptedData, iv)
        return try {
            Json.decodeFromString(
                deserializer = UserData.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserData, output: OutputStream) {
        securityManager.encryptData(
            securityKeyAlias,
            Json.encodeToString(
                serializer = UserData.serializer(),
                value = t
            )
        ).let {
            output.write(it.second + it.first)
        }
    }
}