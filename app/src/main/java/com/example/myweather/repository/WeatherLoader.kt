package com.example.myweather.repository

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.myweather.view.details.DetailsFragment
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherLoader(private val onServerResponseListener: OnServerResponse) {
    private val detailsFragment= DetailsFragment()

    fun loadWeather(lat: Double, lon: Double) {


        Thread {

            val urlText = "http://212.86.114.27/v2/informers?lat=$lat&lon=$lon"
            val uri = URL(urlText)
            val urlConnection: HttpURLConnection =
                (uri.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(
                        "X-Yandex-API-Key",
                        "e9272a49-8eee-47da-ba81-4ad3da6a4b24"
                    )
                }
            val responseCode = urlConnection.responseCode
            val responseMessage = urlConnection.responseMessage

            val clientSide: IntRange = 400..499
            val responseOk: IntRange = 0..399
            val serverSide: IntRange = 500..599



            if (responseCode in serverSide){
                Toast.makeText(detailsFragment.requireContext(),"Что-то не так c сервером",Toast.LENGTH_SHORT).show()

            }else if (responseCode in clientSide){
                Toast.makeText(detailsFragment.requireContext(),"Что-то не так",Toast.LENGTH_SHORT).show()


            }else if (responseCode in responseOk) {
                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                Handler(Looper.getMainLooper()).post {
                    onServerResponseListener.onResponse(weatherDTO)
                }
            }
        }.start()

    }
}
