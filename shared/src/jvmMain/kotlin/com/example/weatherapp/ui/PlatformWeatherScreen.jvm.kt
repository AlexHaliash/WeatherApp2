package com.example.weatherapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherViewModel

@Composable
actual fun PlatformWeatherScreen(viewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Погода (Linux / Desktop)",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = viewModel.cityInput,
            onValueChange = viewModel::updateCityInput,
            label = { Text("Город") },
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), RoundedCornerShape(4.dp)),
            enabled = !viewModel.isLoading,
            singleLine = true,
        )

        Button(
            onClick = viewModel::searchWeather,
            modifier = Modifier.padding(top = 12.dp),
            enabled = !viewModel.isLoading,
        ) {
            Text("Поиск")
        }

        Spacer(modifier = Modifier.height(12.dp))
        WeatherLoadingIndicator(viewModel.isLoading)
        viewModel.errorMessage?.let { WeatherErrorBanner(it) }

        viewModel.weatherData?.let { weather ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outline), RoundedCornerShape(4.dp)),
                shape = RoundedCornerShape(4.dp),
                color = MaterialTheme.colorScheme.surface,
            ) {
                WeatherDetailsContent(weather = weather)
            }
        }

        WeatherEmptyHint(viewModel)
    }
}
