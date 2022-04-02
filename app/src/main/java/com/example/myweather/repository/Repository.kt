package com.example.myweather.repository

interface Repository {

    fun getWeatherFromServer(): Weather

    fun getWeatherFromLocalStorage() : Weather
}