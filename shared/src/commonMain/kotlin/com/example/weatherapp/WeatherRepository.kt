package com.example.weatherapp

class WeatherRepository(
    private val api: WeatherApi,
    private val cache: WeatherCacheManager,
) {
    suspend fun loadWeather(city: String): Result<WeatherResponse> {
        val normalized = city.trim()
        if (normalized.isBlank()) {
            return Result.failure(IllegalArgumentException("Введите название города"))
        }

        val cached = cache.getWeather(normalized)
        return try {
            val fresh = api.getWeather(normalized)
            cache.saveWeather(normalized, fresh)
            Result.success(fresh)
        } catch (e: Exception) {
            if (cached != null) {
                Result.success(cached)
            } else {
                Result.failure(e)
            }
        }
    }

    fun getSavedCities(): List<String> =
        cache.getAllCachedCities().ifEmpty { listOf("Moscow", "London", "Berlin") }
}
