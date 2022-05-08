package com.example.myweather.repository

import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.utils.KEY_YANDEX_DOMAIN_HARD
import com.example.myweather.utils.KEY_YANDEX_KEY
import com.example.myweather.utils.KEY_YANDEX_KEY_VALUE
import com.example.myweather.utils.convertDtoToModel
import com.example.myweather.viewmodel.DetailsViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class DetailsRepositoryOkHttpImpl : DetailsRepository {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(KEY_YANDEX_KEY, KEY_YANDEX_KEY_VALUE)
        builder.url("${KEY_YANDEX_DOMAIN_HARD}lat=${city.lat}&lon=${city.lon}")
        val request = builder.build()

        val call = client.newCall(request)

        Thread {
            val response = call.execute()
            if (response.isSuccessful) {
                val serverResponse = response.body()!!.string()
                val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                val weather = convertDtoToModel(weatherDTO)
                weather.city = city
                callback.onResponse(weather)

            } else {

            }

        }.start()


    }
}
