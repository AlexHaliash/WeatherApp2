package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun PlatformWeatherScreen(viewModel: WeatherViewModel) {
    var searchActive by remember { mutableStateOf(false) }

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
            modifier = Modifier.padding(bottom = 12.dp),
        )

        if (viewModel.savedCities.isNotEmpty()) {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                viewModel.savedCities.take(3).forEachIndexed { index, city ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = 3),
                        onClick = { viewModel.selectSavedCity(index) },
                        selected = viewModel.selectedCityIndex == index,
                        label = { Text(city) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        SearchBar(
            inputField = {
                androidx.compose.material3.SearchBarDefaults.InputField(
                    query = viewModel.cityInput,
                    onQueryChange = viewModel::updateCityInput,
                    onSearch = {
                        searchActive = false
                        viewModel.searchWeather()
                    },
                    expanded = searchActive,
                    onExpandedChange = { searchActive = it },
                    placeholder = { Text("Город") },
                )
            },
            expanded = searchActive,
            onExpandedChange = { searchActive = it },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Нажмите Enter / поиск на клавиатуре",
                modifier = Modifier.padding(16.dp),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        WeatherLoadingIndicator(viewModel.isLoading)
        viewModel.errorMessage?.let { WeatherErrorBanner(it) }

        viewModel.weatherData?.let { weather ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            ) {
                WeatherDetailsContent(weather = weather)
            }
        }

        WeatherEmptyHint(viewModel)
    }
}
