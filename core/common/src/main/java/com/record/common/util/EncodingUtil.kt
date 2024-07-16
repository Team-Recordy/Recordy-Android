package com.record.common.util

fun toUTF8HexString(input: String): String {
    val utf8Bytes = input.toByteArray(Charsets.UTF_8)
    return utf8Bytes.joinToString(separator = "") { byte -> "%02x".format(byte) }
}
