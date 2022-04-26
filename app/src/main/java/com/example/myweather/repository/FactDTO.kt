package com.example.myweather.repository

data class FactDTO(
    val condition: String,
    val daytime: String,
    val feels_like: Int,
    val humidity: Int,
    val icon: String,
    val obs_time: Int,
    val polar: Boolean,
    val pressure_mm: Int,
    val pressure_pa: Int,
    val season: String,
    val temp: Int,
    val wind_dir: String,
    val wind_gust: Int,
    val wind_speed: Int
)