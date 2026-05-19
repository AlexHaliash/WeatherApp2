package com.example.weatherapp.ui

import androidx.compose.runtime.Composable
import com.example.weatherapp.WeatherViewModel

@Composable
expect fun PlatformWeatherScreen(viewModel: WeatherViewModel)
