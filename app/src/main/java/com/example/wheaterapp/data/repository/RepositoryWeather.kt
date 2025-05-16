package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.RetrofitInstance

class WeatherRepository {
    suspend fun getWeather(city: String, apiKey: String) =
        RetrofitInstance.api.getWeatherByCity(city, apiKey)
}
