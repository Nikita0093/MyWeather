package com.example.myweather.repository

import com.example.myweather.repository.dto.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}