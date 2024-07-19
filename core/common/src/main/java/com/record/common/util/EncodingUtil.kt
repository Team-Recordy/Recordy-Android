package com.record.common.util

import java.util.Base64

fun toUTF8HexString(input: String): String {
    val utf8Bytes = input.toByteArray(Charsets.UTF_8)
    return utf8Bytes.joinToString(separator = "") { byte -> "%02x".format(byte) }
}

fun toUTF8(input: String): String {
    val bytes = input.toByteArray(Charsets.UTF_8)

    val base64EncodedString = Base64.getEncoder().encodeToString(bytes)

    return base64EncodedString
}

fun encodingString(contentValue: String): String {
    val bytes = contentValue.toByteArray(Charsets.UTF_8)
    val encodedString = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
    return encodedString
}
