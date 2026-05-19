package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertTrue

class SharedLogicDesktopTest {

    @Test
    fun openWeatherIconUrl_containsHost() {
        assertTrue(WeatherApi().weatherIconUrl("01d").contains("openweathermap.org"))
    }
}
