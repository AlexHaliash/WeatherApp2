package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertEquals

class SharedCommonTest {

    @Test
    fun sampleWeather_hasExpectedTemperature() {
        assertEquals(5.5, sampleWeatherResponse().main.temp)
    }
}
