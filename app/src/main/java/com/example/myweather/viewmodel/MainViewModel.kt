package com.example.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.repository.Repository
import com.example.myweather.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) :
    ViewModel() {


    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getRussianWeather() {
        getWeather(true)

    }

    fun getWorldWeather() {
        getWeather(false)

    }


    private fun getWeather(isRussian: Boolean) {
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(1000L)
            with(repository) {
                if (true) {
                    val answer = if (isRussian) {
                        this.getRussianWeatherFromLocalStorage()
                    } else {
                        this.getWorldWeatherFromLocalStorage()
                    }
                    liveData.postValue(AppState.Success(answer))
                } else {
                    liveData.postValue(AppState.Error(IllegalArgumentException()))
                }
            }
        }.start()

    }
}