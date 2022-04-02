package com.example.myweather.repository

data class Weather(
    var city: City = getDefaultCity(),
    var temperature: Int = 10,
    var temperatureFeelLike: Int = 5
)


data class City(var name: String)

fun getDefaultCity() = City("Moscow")
