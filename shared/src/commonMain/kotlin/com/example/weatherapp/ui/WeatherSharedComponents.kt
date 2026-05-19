package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherApi
import com.example.weatherapp.WeatherResponse
import com.example.weatherapp.WeatherViewModel
import com.example.weatherapp.getWeatherEmoji
import com.example.weatherapp.iconCodeToEmoji

@Composable
fun WeatherLoadingIndicator(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(32.dp))
    }
}

@Composable
fun WeatherErrorBanner(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    )
}

@Composable
fun WeatherDetailsContent(
    weather: WeatherResponse,
    api: WeatherApi = WeatherApi(),
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = weather.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${weather.main.temp.toInt()}°C",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Ощущается как ${weather.main.feelsLike.toInt()}°C",
            fontSize = 16.sp,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(16.dp))
        val condition = weather.weather.firstOrNull()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            WeatherIcon(
                iconCode = condition?.icon.orEmpty(),
                description = condition?.main.orEmpty(),
                api = api,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = condition?.description?.replaceFirstChar { it.uppercase() }.orEmpty(),
                fontSize = 18.sp,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            WeatherMetric("💧", "${weather.main.humidity}%", "Влажность")
            WeatherMetric("💨", "${weather.wind.speed} м/с", "Ветер")
        }
    }
}

@Composable
private fun WeatherMetric(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 24.sp)
        Text(text = value)
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun WeatherIcon(
    iconCode: String,
    description: String,
    api: WeatherApi,
) {
    val emoji = if (iconCode.isNotBlank()) iconCodeToEmoji(iconCode) else getWeatherEmoji(description)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 48.sp)
        if (iconCode.isNotBlank()) {
            Text(
                text = "OWM: $iconCode",
                fontSize = 10.sp,
                color = Color.Gray,
            )
        }
    }
}

@Composable
fun WeatherEmptyHint(viewModel: WeatherViewModel) {
    if (viewModel.weatherData == null && !viewModel.isLoading && viewModel.errorMessage == null) {
        Text(
            text = "Введите город и нажмите поиск",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(32.dp),
        )
    }
}
