package com.example.myweather.repository

import com.example.myweather.viewmodel.DetailsViewModel

interface DetailsRepositoryAll {
    fun getAllWeatherDetails(callback: DetailsViewModel.CallbackForAll)
}