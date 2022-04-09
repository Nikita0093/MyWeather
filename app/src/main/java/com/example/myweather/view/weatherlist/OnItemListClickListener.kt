package com.example.myweather.view.weatherlist

import com.example.myweather.repository.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}