package com.example.myweather.repository

import android.os.Handler
import android.os.Looper
import com.example.myweather.view.details.DetailsFragment
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onServerResponseListener: OnServerResponse) {

    fun loadWeather(lat: Double, lon: Double) {


        Thread {
            try {

                val urlText = "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon"
                val uri = URL(urlText)
                val urlConnection: HttpsURLConnection =
                    (uri.openConnection() as HttpsURLConnection).apply {
                        connectTimeout = 1000
                        readTimeout = 1000
                        addRequestProperty(
                            "X-Yandex-API-Key",
                            "e9272a49-8eee-47da-ba81-4ad3da6a4b24"
                        )
                    }

                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                Handler(Looper.getMainLooper()).post {
                    onServerResponseListener.onResponse(weatherDTO)
                }
            } catch (e: Exception) {


            }
        }.start()
    }
}