package com.example.myweather.repository

class RepositoryImpl : Repository {
    override fun getWeatherFromServer() = Weather()


    override fun getWorldWeatherFromLocalStorage() = getWorldCities()


    override fun getRussianWeatherFromLocalStorage() = getRussianCities()

}

