package com.record.datastore

import androidx.datastore.core.Serializer
import com.record.common.security.SecurityInterface
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class RecordyLocalDataSerializer @Inject constructor(
    private val securityManager: SecurityInterface,
) : Serializer<RecordyLocalData> {
    private val securityKeyAlias = "data-store"
    override val defaultValue: RecordyLocalData
        get() = RecordyLocalData()

    override suspend fun readFrom(input: InputStream): RecordyLocalData {
        val encryptedDataWithIv = input.readBytes()
        if (encryptedDataWithIv.size < 12) {
            return defaultValue
        }
        val iv = encryptedDataWithIv.copyOfRange(0, 12)
        val encryptedData = encryptedDataWithIv.copyOfRange(12, encryptedDataWithIv.size)
        val decryptedBytes = securityManager.decryptData(securityKeyAlias, encryptedData, iv)
        return try {
            Json.decodeFromString(
                deserializer = RecordyLocalData.serializer(),
                string = decryptedBytes.decodeToString(),
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: RecordyLocalData, output: OutputStream) {
        securityManager.encryptData(
            securityKeyAlias,
            Json.encodeToString(
                serializer = RecordyLocalData.serializer(),
                value = t,
            ),
        ).let {
            output.write(it.second + it.first)
        }
    }
}
