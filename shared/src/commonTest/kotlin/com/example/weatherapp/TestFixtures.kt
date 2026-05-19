package com.example.weatherapp

const val SAMPLE_WEATHER_JSON = """
{
  "name": "Moscow",
  "main": {"temp": 5.5, "humidity": 72, "feels_like": 2.0},
  "weather": [{"description": "пасмурно", "icon": "04d", "main": "Clouds"}],
  "wind": {"speed": 3.2},
  "dt": 1710000000
}
"""

fun sampleWeatherResponse(): WeatherResponse = WeatherResponse(
    name = "Moscow",
    main = MainData(temp = 5.5, humidity = 72, feelsLike = 2.0),
    weather = listOf(Weather(description = "пасмурно", icon = "04d", main = "Clouds")),
    wind = Wind(speed = 3.2),
    timestamp = 1710000000,
)
