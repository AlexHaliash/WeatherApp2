package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherViewModel

@Composable
actual fun PlatformWeatherScreen(viewModel: WeatherViewModel) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val columns = when {
            maxWidth < 600.dp -> 1
            maxWidth < 960.dp -> 2
            else -> 3
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Погода — Web",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.cityInput,
                        onValueChange = viewModel::updateCityInput,
                        label = { Text("Город") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !viewModel.isLoading,
                        singleLine = true,
                    )
                    Button(
                        onClick = viewModel::searchWeather,
                        modifier = Modifier.padding(top = 8.dp),
                        enabled = !viewModel.isLoading,
                    ) {
                        Text("Поиск")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    WeatherLoadingIndicator(viewModel.isLoading)
                    viewModel.errorMessage?.let { WeatherErrorBanner(it) }
                    WeatherEmptyHint(viewModel)
                }
            }

            viewModel.weatherData?.let { weather ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                    ) {
                        WeatherDetailsContent(weather = weather)
                    }
                }
            }

            items(viewModel.savedCities.take(columns * 2)) { city ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = city,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}
