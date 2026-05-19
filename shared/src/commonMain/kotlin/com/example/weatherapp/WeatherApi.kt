package com.example.weatherapp

import com.example.weatherapp.network.createWeatherHttpClient
import com.example.weatherapp.network.openWeatherApiKey
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApi(
    private val client: HttpClient = createWeatherHttpClient(),
    private val apiKey: String = openWeatherApiKey,
) {
    private val baseUrl = "https://api.openweathermap.org/data/2.5"

    suspend fun getWeather(city: String): WeatherResponse {
        return client.get("$baseUrl/weather") {
            parameter("q", city)
            parameter("appid", apiKey)
            parameter("units", "metric")
            parameter("lang", "ru")
        }.body()
    }

    fun weatherIconUrl(iconCode: String): String =
        "https://openweathermap.org/img/wn/${iconCode}@2x.png"
}
