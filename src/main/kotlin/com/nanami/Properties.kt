package com.nanami

import com.natpryce.konfig.ConfigurationMap
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType
import java.io.File

object Properties {
    private val localProperties =
        ConfigurationMap(
            mapOf(
                "HOST" to "localhost",
                "PORT" to "8080",
            ),
        )
    private val config = ConfigurationProperties.fromFile(File("env.properties")) overriding localProperties

    private operator fun get(key: String): String = config[Key(key, stringType)]

    data object Server {
        val host = get("HOST")
        val port = get("PORT").toInt()
    }

    data object ThirdParty {
        val starWarsUrl: String = get("API_URL")
    }
}
