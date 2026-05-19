package com.example.weatherapp

import com.russhwolf.settings.MapSettings
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WeatherCacheManagerTest {

    @Test
    fun saveAndGet_returnsCachedWeather() {
        val cache = WeatherCacheManager(MapSettings())
        cache.saveWeather("Moscow", sampleWeatherResponse())
        assertEquals("Moscow", cache.getWeather("Moscow")?.name)
    }

    @Test
    fun getWeather_isCaseInsensitive() {
        val cache = WeatherCacheManager(MapSettings())
        cache.saveWeather("London", sampleWeatherResponse().copy(name = "London"))
        assertNotNull(cache.getWeather("LONDON"))
    }

    @Test
    fun clearCache_removesAllEntries() {
        val cache = WeatherCacheManager(MapSettings())
        cache.saveWeather("Berlin", sampleWeatherResponse().copy(name = "Berlin"))
        cache.clearCache()
        assertNull(cache.getWeather("Berlin"))
    }

    @Test
    fun getAllCachedCities_returnsSavedCityNames() {
        val cache = WeatherCacheManager(MapSettings())
        cache.saveWeather("Paris", sampleWeatherResponse().copy(name = "Paris"))
        assertTrue(cache.getAllCachedCities().contains("Paris"))
    }

    @Test
    fun persistAndReload_keepsDataInNewManagerInstance() {
        val settings = MapSettings()
        WeatherCacheManager(settings).apply {
            saveWeather("Oslo", sampleWeatherResponse().copy(name = "Oslo"))
        }
        val reloaded = WeatherCacheManager(settings)
        assertEquals("Oslo", reloaded.getWeather("Oslo")?.name)
    }
}
