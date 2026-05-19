package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json

class WeatherModelsTest {

  private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun deserializeWeatherResponse_parsesCityAndTemperature() {
        val response = json.decodeFromString<WeatherResponse>(SAMPLE_WEATHER_JSON)
        assertEquals("Moscow", response.name)
        assertEquals(5.5, response.main.temp)
    }

    @Test
    fun deserializeWeatherResponse_parsesHumidityAndWind() {
        val response = json.decodeFromString<WeatherResponse>(SAMPLE_WEATHER_JSON)
        assertEquals(72, response.main.humidity)
        assertEquals(3.2, response.wind.speed)
    }

    @Test
    fun deserializeWeatherResponse_parsesCondition() {
        val response = json.decodeFromString<WeatherResponse>(SAMPLE_WEATHER_JSON)
        assertEquals("Clouds", response.weather.first().main)
        assertEquals("04d", response.weather.first().icon)
    }

    @Test
    fun serializeCachedWeather_roundTrip() {
        val cached = CachedWeather("moscow", sampleWeatherResponse(), 1000L)
        val encoded = json.encodeToString(CachedWeather.serializer(), cached)
        val decoded = json.decodeFromString(CachedWeather.serializer(), encoded)
        assertEquals(cached.city, decoded.city)
        assertEquals(cached.weatherData.name, decoded.weatherData.name)
    }

    @Test
    fun weatherApi_iconUrl_usesOpenWeatherCdn() {
        val api = WeatherApi()
        assertTrue(api.weatherIconUrl("04d").contains("openweathermap.org"))
    }
}
