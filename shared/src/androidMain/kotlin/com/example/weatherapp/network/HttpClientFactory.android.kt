package com.example.weatherapp.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android

actual fun createPlatformHttpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient =
    HttpClient(Android, config)
