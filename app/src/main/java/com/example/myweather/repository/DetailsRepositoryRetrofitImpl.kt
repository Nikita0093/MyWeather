package com.example.myweather.repository

import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.utils.KEY_YANDEX_DOMAIN_HARD
import com.example.myweather.utils.KEY_YANDEX_KEY_VALUE
import com.example.myweather.utils.YANDEX_LANG
import com.example.myweather.utils.convertDtoToModel
import com.example.myweather.viewmodel.DetailsViewModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryRetrofitImpl : DetailsRepository {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val weatherApi = Retrofit.Builder().apply {
            baseUrl(KEY_YANDEX_DOMAIN_HARD)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherApi::class.java)
        weatherApi.getWeather(KEY_YANDEX_KEY_VALUE, city.lat, city.lon, YANDEX_LANG)
            .enqueue(object : Callback<WeatherDTO> {
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val weather = convertDtoToModel(it)
                            weather.city = city
                            callback.onResponse(weather)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, error: Throwable) {
                    callback.onFailure(error)
                }

            })
    }
}
