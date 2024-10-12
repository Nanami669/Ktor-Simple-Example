package com.nanami

import com.nanami.plugins.configureRouting
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import org.slf4j.event.Level

fun main() {
    embeddedServer(Netty, port = Properties.Server.port, host = Properties.Server.host, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    config()
    configureRouting()
}

fun Application.config() {
    install(CallLogging) {
        level = Level.INFO
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                explicitNulls = false
            },
        )
    }
}
