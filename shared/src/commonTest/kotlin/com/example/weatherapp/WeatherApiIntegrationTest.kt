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

class WeatherApiIntegrationTest {

    private fun mockClient(body: String = SAMPLE_WEATHER_JSON): HttpClient {
        val engine = MockEngine {
            respond(
                content = body,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun getWeather_withMockEngine_returnsParsedCity() = runTest {
        val api = WeatherApi(client = mockClient(), apiKey = "test-key")
        val result = api.getWeather("Moscow")
        assertEquals("Moscow", result.name)
    }

    @Test
    fun getWeather_withMockEngine_returnsHumidity() = runTest {
        val api = WeatherApi(client = mockClient(), apiKey = "test-key")
        val result = api.getWeather("Moscow")
        assertEquals(72, result.main.humidity)
    }

    @Test
    fun getWeather_withMockEngine_returnsWindSpeed() = runTest {
        val api = WeatherApi(client = mockClient(), apiKey = "test-key")
        val result = api.getWeather("Moscow")
        assertEquals(3.2, result.wind.speed)
    }

    @Test
    fun getWeather_endToEndWithCache_storesResponse() = runTest {
        val cache = WeatherCacheManager(MapSettings())
        val api = WeatherApi(client = mockClient(), apiKey = "test-key")
        val weather = api.getWeather("Moscow")
        cache.saveWeather("Moscow", weather)
        assertEquals("Moscow", cache.getWeather("Moscow")?.name)
        assertTrue(cache.getAllCachedCities().contains("Moscow"))
    }
}
