package com.example.weatherapp

import com.russhwolf.settings.MapSettings
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json

class WeatherRepositoryTest {

    private fun repositoryWithMockApi(): WeatherRepository {
        val client = HttpClient(
            MockEngine {
                respond(
                    content = SAMPLE_WEATHER_JSON,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            },
        ) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        return WeatherRepository(
            api = WeatherApi(client = client, apiKey = "test"),
            cache = WeatherCacheManager(MapSettings()),
        )
    }

    @Test
    fun loadWeather_withBlankCity_returnsFailure() = runTest {
        val result = repositoryWithMockApi().loadWeather("  ")
        assertTrue(result.isFailure)
        assertEquals("Введите название города", result.exceptionOrNull()?.message)
    }

    @Test
    fun loadWeather_withMockApi_returnsMoscow() = runTest {
        val result = repositoryWithMockApi().loadWeather("Moscow")
        assertTrue(result.isSuccess)
        assertEquals("Moscow", result.getOrNull()?.name)
    }

    @Test
    fun loadWeather_cachesResult() = runTest {
        val repo = repositoryWithMockApi()
        repo.loadWeather("Moscow")
        assertTrue(repo.getSavedCities().contains("Moscow"))
    }
}
