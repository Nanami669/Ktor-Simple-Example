@file:Suppress("ktlint:standard:filename")

package com.nanami

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TmdbClient {
    suspend fun get(url: String) = httpClient.get(url)

    private val httpClient =
        HttpClient(Apache) {
            expectSuccess = false

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                        explicitNulls = false
                    },
                )
            }
        }
}
