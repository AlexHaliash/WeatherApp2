package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherUtilsTest {

    @Test
    fun getWeatherEmoji_clear_returnsSun() {
        assertEquals("☀️", getWeatherEmoji("Clear"))
    }

    @Test
    fun getWeatherEmoji_rain_returnsRainCloud() {
        assertEquals("🌧", getWeatherEmoji("rain"))
    }

    @Test
    fun getWeatherEmoji_unknown_returnsThermometer() {
        assertEquals("🌡", getWeatherEmoji("unknown"))
    }
}
