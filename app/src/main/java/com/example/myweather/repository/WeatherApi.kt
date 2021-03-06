package com.example.myweather.repository

import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET(KEY_YANDEX_DOMAIN_HARD_ENDPOINT)
    fun getWeather(
        @Header(KEY_YANDEX_KEY) apikey: String,
        @Query(YANDEX_LAT) lat: Double,
        @Query(YANDEX_LON) lon: Double,
        @Query(YANDEX_LANG) lang: String

    ): Call<WeatherDTO>
}