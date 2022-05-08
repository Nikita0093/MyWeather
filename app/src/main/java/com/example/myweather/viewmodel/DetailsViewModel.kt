package com.example.myweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.repository.*

class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private var repository: DetailsRepository = DetailsRepositoryOkHttpImpl()
) :
    ViewModel() {
    fun getLiveData() = liveData

    fun getWeather(city: City) {
        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
            }

            override fun onFailure(e: Throwable) {
                liveData.postValue(DetailsState.Error(e))
            }
        })
    }

    interface Callback {

        fun onResponse(weather: Weather) {

        }

        fun onFailure(e: Throwable)

    }
}
