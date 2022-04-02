package com.example.myweather.viewmodel

import com.example.myweather.repository.Weather

sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
}