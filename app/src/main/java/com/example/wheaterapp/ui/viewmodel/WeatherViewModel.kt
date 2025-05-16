package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                _weatherData.postValue(response)
                _error.postValue(null)
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
