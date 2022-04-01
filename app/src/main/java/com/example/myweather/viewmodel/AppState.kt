package com.example.myweather.viewmodel

sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherData: Any) : AppState()
    data class Error(val error: Throwable) : AppState()
}