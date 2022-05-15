package com.example.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.repository.DetailsRepositoryRoomImpl
import com.example.myweather.repository.Weather

class HistoryViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepositoryRoomImpl = DetailsRepositoryRoomImpl()
) :
    ViewModel() {


    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getAll() {
        repository.getAllWeatherDetails(object : CallbackForAll {
            override fun onResponse(listWeather: List<Weather>) {
                liveData.postValue(AppState.Success(listWeather))
            }

            override fun onFailure(e: Throwable) {
                liveData.postValue(AppState.Error(e))
            }

        })
    }

    interface CallbackForAll {

        fun onResponse(listWeather: List<Weather>)


        fun onFailure(e: Throwable)

    }

}