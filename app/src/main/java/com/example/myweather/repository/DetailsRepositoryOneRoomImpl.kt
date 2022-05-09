package com.example.myweather.repository

import com.example.myweather.MyApp
import com.example.myweather.utils.convertHistoryEntityToWeather
import com.example.myweather.viewmodel.DetailsViewModel

class DetailsRepositoryOneRoomImpl : DetailsRepositoryOne, DetailsRepositoryAll {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        TODO("Not yet implemented")
    }

    override fun getAllWeatherDetails(callback: DetailsViewModel.CallbackForAll) {
        callback.onResponse(convertHistoryEntityToWeather(MyApp.getHistoryDao().getAll()))
    }


}
