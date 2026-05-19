package com.example.weatherapp

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndroidUiStringsTest {

    @Test
    fun iconCode_01d_isSun() {
        assertEquals("☀️", iconCodeToEmoji("01d"))
    }

    @Test
    fun iconCode_10n_isRainSun() {
        assertEquals("🌦", iconCodeToEmoji("10n"))
    }

    @Test
    fun weatherEmoji_clear() {
        assertEquals("☀️", getWeatherEmoji("clear"))
    }

    @Test
    fun openWeatherIconUrl_format() {
        assertTrue(WeatherApi().weatherIconUrl("04d").endsWith("@2x.png"))
    }
}
