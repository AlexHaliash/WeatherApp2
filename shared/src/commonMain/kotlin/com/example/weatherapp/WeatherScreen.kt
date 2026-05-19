package com.example.weatherapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.PlatformWeatherScreen

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    PlatformWeatherScreen(viewModel)
}

fun getWeatherEmoji(main: String): String {
    return when (main.lowercase()) {
        "clear" -> "☀️"
        "clouds" -> "☁️"
        "rain" -> "🌧"
        "drizzle" -> "🌦"
        "thunderstorm" -> "⛈"
        "snow" -> "❄️"
        "mist", "fog" -> "🌫"
        else -> "🌡"
    }
}

/** Maps OpenWeatherMap icon codes (01d, 10n, …) to emoji for multiplatform display. */
fun iconCodeToEmoji(iconCode: String): String = when {
    iconCode.startsWith("01") -> "☀️"
    iconCode.startsWith("02") -> "🌤"
    iconCode.startsWith("03") -> "☁️"
    iconCode.startsWith("04") -> "☁️"
    iconCode.startsWith("09") -> "🌧"
    iconCode.startsWith("10") -> "🌦"
    iconCode.startsWith("11") -> "⛈"
    iconCode.startsWith("13") -> "❄️"
    iconCode.startsWith("50") -> "🌫"
    else -> "🌡"
}
