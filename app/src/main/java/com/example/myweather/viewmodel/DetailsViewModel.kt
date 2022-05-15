package com.example.myweather.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.repository.*


class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private var repositoryOne: DetailsRepositoryOne = DetailsRepositoryOneRetrofitImpl(),
    private var repositoryAdd: DetailsRepositoryAdd = DetailsRepositoryRoomImpl()
) :
    ViewModel() {
    fun getLiveData() = liveData

    fun getWeather(city: City) {
        liveData.postValue(DetailsState.Loading)
        if (isInternet()) {
            repositoryOne = DetailsRepositoryOneRetrofitImpl()
            repositoryOne.getWeatherDetails(city, object : Callback {
                override fun onResponse(weather: Weather) {
                    liveData.postValue(DetailsState.Success(weather))
                    repositoryAdd.addWeather(weather)
                }

                override fun onFailure(e: Throwable) {
                    liveData.postValue(DetailsState.Error(e))
                }

                override fun onDisconnect(x: String) {
                    liveData.postValue(DetailsState.Disconnect(x))
                }
            })

        } else {
            repositoryOne = DetailsRepositoryRoomImpl()

            repositoryOne.getWeatherDetails(city, object : Callback {
                override fun onResponse(weather: Weather) {
                    liveData.postValue(DetailsState.Success(weather))
                }

                override fun onFailure(e: Throwable) {
                    liveData.postValue(DetailsState.Error(e))
                }

                override fun onDisconnect(x: String) {
                    liveData.postValue(DetailsState.Disconnect(x))
                }
            })


        }


    }

    private fun isInternet(): Boolean
    {
        return true

            }


    interface Callback {

        fun onResponse(weather: Weather)

        fun onFailure(e: Throwable)

        fun onDisconnect(x: String)

    }

}
