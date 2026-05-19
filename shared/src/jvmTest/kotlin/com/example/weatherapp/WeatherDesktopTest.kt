package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherDesktopTest {

    @Test
    fun platformKind_isDesktop() {
        assertEquals(PlatformKind.Desktop, currentPlatformKind)
    }

    @Test
    fun cacheManager_persistsOnJvm() {
        val settings = com.russhwolf.settings.Settings()
        WeatherCacheManager(settings).saveWeather("Tallinn", sampleWeatherResponse().copy(name = "Tallinn"))
        assertEquals("Tallinn", WeatherCacheManager(settings).getWeather("Tallinn")?.name)
    }

    @Test
    fun getWeatherEmoji_snow_onJvm() {
        assertEquals("❄️", getWeatherEmoji("snow"))
    }
}
