package com.example.weatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository(WeatherApi(), WeatherCacheManager()),
) : ViewModel() {
    var cityInput by mutableStateOf("")
        private set
    var weatherData by mutableStateOf<WeatherResponse?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var savedCities by mutableStateOf<List<String>>(emptyList())
        private set
    var selectedCityIndex by mutableIntStateOf(-1)
        private set

    init {
        refreshSavedCities()
    }

    fun updateCityInput(newCity: String) {
        cityInput = newCity
        selectedCityIndex = -1
    }

    fun searchWeather() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            repository.loadWeather(cityInput)
                .onSuccess { weather ->
                    weatherData = weather
                    refreshSavedCities()
                }
                .onFailure { error ->
                    if (weatherData == null) {
                        errorMessage = "Ошибка: ${error.message ?: "Не удалось загрузить погоду"}"
                    } else {
                        errorMessage = "Показаны кешированные данные. Сеть: ${error.message}"
                    }
                }

            isLoading = false
        }
    }

    fun selectSavedCity(index: Int) {
        if (index !in savedCities.indices) return
        selectedCityIndex = index
        cityInput = savedCities[index]
        searchWeather()
    }

    fun clearError() {
        errorMessage = null
    }

    private fun refreshSavedCities() {
        savedCities = repository.getSavedCities()
    }
}
