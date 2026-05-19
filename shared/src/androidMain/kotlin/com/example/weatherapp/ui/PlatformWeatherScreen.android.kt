package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherViewModel

@Composable
actual fun PlatformWeatherScreen(viewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Погода",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = viewModel.cityInput,
            onValueChange = viewModel::updateCityInput,
            label = { Text("Название города") },
            placeholder = { Text("Moscow, London…") },
            leadingIcon = { Text("🔍") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading,
            singleLine = true,
        )

        Button(
            onClick = viewModel::searchWeather,
            modifier = Modifier.padding(top = 12.dp),
            enabled = !viewModel.isLoading,
        ) {
            Text("Узнать погоду")
        }

        Spacer(modifier = Modifier.height(12.dp))
        WeatherLoadingIndicator(viewModel.isLoading)
        viewModel.errorMessage?.let { WeatherErrorBanner(it) }

        viewModel.weatherData?.let { weather ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                WeatherDetailsContent(weather = weather)
            }
        }

        WeatherEmptyHint(viewModel)
    }
}
