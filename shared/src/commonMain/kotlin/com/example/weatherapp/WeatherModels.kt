package com.example.weatherapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String,
    val main: MainData,
    val weather: List<Weather>,
    val wind: Wind,
    @SerialName("dt") val timestamp: Long
)

@Serializable
data class MainData(
    val temp: Double,
    val humidity: Int,
    @SerialName("feels_like") val feelsLike: Double
)

@Serializable
data class Weather(
    val description: String,
    val icon: String,
    val main: String
)

@Serializable
data class Wind(
    val speed: Double
)

@Serializable
data class CachedWeather(
    val city: String,
    val weatherData: WeatherResponse,
    val timestamp: Long
)