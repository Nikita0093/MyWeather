package com.example.myweather.repository

import android.os.Handler
import android.os.Looper
import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.utils.KEY_YANDEX_DOMAIN_HARD
import com.example.myweather.utils.KEY_YANDEX_KEY
import com.example.myweather.utils.KEY_YANDEX_KEY_VALUE
import com.example.myweather.view.details.DetailsFragment
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherLoader(private val onServerResponseListener: OnServerResponse) {
    private val detailsFragment = DetailsFragment()

    fun loadWeather(lat: Double, lon: Double) {


        Thread {
            val urlText = "${KEY_YANDEX_DOMAIN_HARD}lat=$lat&lon=$lon"
            val uri = URL(urlText)
            val urlConnection: HttpURLConnection =
                (uri.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(
                        KEY_YANDEX_KEY,
                        KEY_YANDEX_KEY_VALUE
                    )
                }

            try {
                val responseCode = urlConnection.responseCode
                val responseMessage = urlConnection.responseMessage

                val clientSide: IntRange = 400..499
                val responseOk: IntRange = 200..299
                val serverSide: IntRange = 500..599



                when (responseCode) {
                    in serverSide -> {
                        // Toast.makeText(detailsFragment.requireContext(),"Что-то не так c сервером",Toast.LENGTH_SHORT).show()

                    }
                    in clientSide -> {
                        //Toast.makeText(detailsFragment.requireContext(),"Что-то не так со стороны клиента",Toast.LENGTH_SHORT).show()


                    }
                    in responseOk -> {
                        val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                        val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                        Handler(Looper.getMainLooper()).post {
                            onServerResponseListener.onResponse(weatherDTO)
                            //Toast.makeText(detailsFragment.requireContext(),"Что-то не так c сервером",Toast.LENGTH_SHORT).show()  НЕ ПОНИМАЮ ОТКУДА ДОБЫВАТЬ КОНТЕКСТ...
                        }
                    }
                }


            } catch (e: JsonSyntaxException) {



            } finally {
                urlConnection.disconnect()
            }


        }.start()
    }
}

