package org.sopt.common.security.fake

import java.security.Provider
import java.security.Security

class FakeAndroidKeyStoreProvider : Provider(
    "AndroidKeyStore",
    1.0,
    "Fake AndroidKeyStore provider"
) {

    init {
        put(
            "KeyStore.AndroidKeyStore",
            FakeKeyStore::class.java.name
        )
        put(
            "KeyGenerator.AES",
            FakeAESKeyGenerator::class.java.name
        )
    }

    companion object {
        fun setup() {
            Security.addProvider(FakeAndroidKeyStoreProvider())
        }
    }
}