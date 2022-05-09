package com.example.myweather.repository

import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.utils.*
import com.example.myweather.viewmodel.DetailsViewModel
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class DetailsRepositoryOneOkHttpImpl : DetailsRepositoryOne {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(KEY_YANDEX_KEY, KEY_YANDEX_KEY_VALUE)
        builder.url("${KEY_YANDEX_DOMAIN_HARD}${KEY_YANDEX_DOMAIN_HARD_ENDPOINT}lat=${city.lat}&lon=${city.lon}")
        val request = builder.build()

        val call = client.newCall(request)

        Thread {
            call.enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    callback.onFailure(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful){
                        val serverResponse = response.body()!!.string()
                        val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                        val weather = convertDtoToModel(weatherDTO)
                        weather.city = city
                        callback.onResponse(weather)

                    }
                }

            })
        }.start()

    }
}
