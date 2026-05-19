package com.example.weatherapp.network

import com.example.weatherapp.config.GeneratedApiKey
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect fun createPlatformHttpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

fun createWeatherHttpClient(): HttpClient = createPlatformHttpClient {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            },
        )
    }
}

internal val openWeatherApiKey: String
    get() = GeneratedApiKey.OPENWEATHER_API_KEY
