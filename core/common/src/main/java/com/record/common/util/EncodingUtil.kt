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

fun decodeHtmlEntities(encodedText: String): String {
    val entitiesMap = mapOf(
        "&lt;" to "<", "&#60;" to "<",
        "&gt;" to ">", "&#62;" to ">",
        "&amp;" to "&", "&#38;" to "&",
        "&quot;" to "\"", "&#34;" to "\"",
        "&apos;" to "'", "&#39;" to "'",
        "&nbsp;" to " ", "&#160;" to " ",
        "&cent;" to "¢", "&#162;" to "¢",
        "&pound;" to "£", "&#163;" to "£",
        "&yen;" to "¥", "&#165;" to "¥",
        "&euro;" to "€", "&#8364;" to "€",
        "&copy;" to "©", "&#169;" to "©",
        "&reg;" to "®", "&#174;" to "®",
    )

    var decodedText = encodedText
    for ((entity, replacement) in entitiesMap) {
        decodedText = decodedText.replace(entity, replacement)
    }
    return decodedText
}
