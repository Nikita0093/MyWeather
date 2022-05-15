package com.example.myweather.repository

import com.example.myweather.MyApp
import com.example.myweather.utils.convertHistoryEntityToWeather
import com.example.myweather.utils.convertWeatherToEntity
import com.example.myweather.viewmodel.DetailsViewModel
import com.example.myweather.viewmodel.HistoryViewModel

class DetailsRepositoryRoomImpl : DetailsRepositoryOne, DetailsRepositoryAll, DetailsRepositoryAdd {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val list = convertHistoryEntityToWeather(MyApp.getHistoryDao().getHistoryForCity(city.name))
        callback.onResponse(list.last())
    }

    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        callback.onResponse(convertHistoryEntityToWeather(MyApp.getHistoryDao().getAll()))
    }

    override fun addWeather(weather: Weather) {
        MyApp.getHistoryDao().insert(convertWeatherToEntity(weather))
    }


}

