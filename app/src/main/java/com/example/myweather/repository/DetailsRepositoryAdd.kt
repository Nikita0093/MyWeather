package com.example.myweather.repository

import com.example.myweather.viewmodel.DetailsViewModel

interface DetailsRepositoryAdd {
    fun addWeather(weather: Weather)
}