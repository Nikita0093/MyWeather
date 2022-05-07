package com.example.myweather.view.details

import android.app.IntentService
import android.content.Intent
import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.utils.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DetailsFragmentService(val name: String = " ") : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val coordinatesLat = it.getDoubleExtra(KEY_BUNDLE_LAT, 0.0)
            val coordinatesLon = it.getDoubleExtra(KEY_BUNDLE_LON, 0.0)

            val urlText = "${KEY_YANDEX_DOMAIN_HARD}lat=$coordinatesLat&lon=$coordinatesLon"
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
                        val errorMessage = Intent(BroadcastReceiver_ERROR_WAVE)
                        errorMessage.putExtra(
                            BroadcastReceiver_ERROR_SERVER_BUNDLE,
                            responseMessage
                        )
                        sendBroadcast(errorMessage)


                    }
                    in clientSide -> {
                        val errorMessage = Intent(BroadcastReceiver_ERROR_WAVE)
                        errorMessage.putExtra(
                            BroadcastReceiver_ERROR_CLIENT_BUNDLE,
                            responseMessage
                        )
                        sendBroadcast(errorMessage)


                    }
                    in responseOk -> {
                        val buffer =
                            BufferedReader(InputStreamReader(urlConnection.inputStream))
                        val weatherDTO: WeatherDTO =
                            Gson().fromJson(buffer, WeatherDTO::class.java)


                        val message = Intent(BroadcastReceiver_WAVE)
                        message.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER, weatherDTO)
                        sendBroadcast(message)
                    }
                }


            } catch (e: JsonSyntaxException) {


            } finally {
                urlConnection.disconnect()
            }

        }
    }
}
