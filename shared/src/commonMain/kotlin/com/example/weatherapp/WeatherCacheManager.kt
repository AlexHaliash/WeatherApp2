package com.example.weatherapp

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.serialization.json.Json

class WeatherCacheManager(
    private val settings: Settings = Settings(),
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private var memoryCache: MutableMap<String, CachedWeather> = loadFromDisk().toMutableMap()

    companion object {
        private const val CACHE_DURATION_MS = 30 * 60 * 1000L
        private const val STORAGE_KEY = "weather_cache_v1"
    }

    fun saveWeather(city: String, weather: WeatherResponse) {
        val key = city.lowercase().trim()
        memoryCache[key] = CachedWeather(
            city = key,
            weatherData = weather,
            timestamp = currentTimeMillis(),
        )
        persist()
    }

    fun getWeather(city: String): WeatherResponse? {
        val key = city.lowercase().trim()
        val cached = memoryCache[key] ?: return null
        val expired = currentTimeMillis() - cached.timestamp > CACHE_DURATION_MS
        return if (expired) {
            memoryCache.remove(key)
            persist()
            null
        } else {
            cached.weatherData
        }
    }

    fun getAllCachedCities(): List<String> =
        memoryCache.values
            .filter { currentTimeMillis() - it.timestamp <= CACHE_DURATION_MS }
            .map { it.weatherData.name }
            .distinct()

    fun clearCache() {
        memoryCache.clear()
        settings.remove(STORAGE_KEY)
    }

    private fun loadFromDisk(): Map<String, CachedWeather> {
        val raw = settings.getStringOrNull(STORAGE_KEY) ?: return emptyMap()
        return runCatching {
            json.decodeFromString<Map<String, CachedWeather>>(raw)
        }.getOrElse { emptyMap() }
    }

    private fun persist() {
        settings[STORAGE_KEY] = json.encodeToString(memoryCache)
    }

}
