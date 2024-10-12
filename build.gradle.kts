import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"

    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.nanami"
version = "0.0.1"

application {
    mainClass.set("com.nanami.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

val konfigVersion = "1.6.10.0"

// Test
val kotestVersion = "5.9.1"
val mockkVersion = "1.13.12"

// Logging
val janinoVersion = "3.1.10"
val kotlinLoggingVersion = "3.0.5"
val logbackVersion = "1.4.12"
val logstashVersion = "7.4"
dependencies {

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-html-builder")

    implementation("io.ktor:ktor-client-content-negotiation-jvm")
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-apache-jvm")

    implementation("com.natpryce:konfig:$konfigVersion")

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    runtimeOnly("org.codehaus.janino:janino:$janinoVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.mockk:mockk-jvm:$mockkVersion")
    testImplementation("io.ktor:ktor-client-mock-jvm")
}

tasks {

    withType<KotlinCompile>().configureEach {
        dependsOn("ktlintFormat")
    }

    withType<ShadowJar>().configureEach {
        enabled = true
        archiveFileName.set("app.jar")
    }

    ("jar") {
        enabled = false
    }

    withType<Test>().configureEach {
        useJUnitPlatform()

        testLogging {
            showExceptions = true
            showStackTraces = true
            exceptionFormat = FULL
            events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        }

        reports.forEach { report -> report.required.value(false) }
    }

    withType<Wrapper> {
        gradleVersion = "8.10"
    }
}
