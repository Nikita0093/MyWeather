package com.example.myweather.utils

import com.example.myweather.repository.Weather
import com.example.myweather.repository.dto.FactDTO
import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.repository.getDefaultCity

const val KEY_BUNDLE_WEATHER = "key"
const val KEY_BUNDLE_LAT = "lat1"
const val KEY_BUNDLE_LON = "lon1"
const val KEY_BUNDLE_SERVICE_BROADCAST_WEATHER = "weatherBroadcast"
const val KEY_YANDEX_DOMAIN_HARD = "http://212.86.114.27/"
const val KEY_YANDEX_DOMAIN_HARD_ENDPOINT = "v2/informers?"
const val KEY_YANDEX_DOMAIN = "https://api.weather.yandex.ru/"
const val KEY_YANDEX_KEY = "X-Yandex-API-Key"
const val KEY_YANDEX_KEY_VALUE = "e9272a49-8eee-47da-ba81-4ad3da6a4b24"
const val BroadcastReceiver_WAVE = "myWave"
const val BroadcastReceiver_ERROR_WAVE = "myErrorWave"
const val BroadcastReceiver_ERROR_SERVER_BUNDLE = "myErrorServerBundle"
const val BroadcastReceiver_ERROR_CLIENT_BUNDLE = "myErrorClientBundle"
const val YANDEX_LAT = "lat"
const val YANDEX_LON = "lon"


class Utils {
}


fun convertDtoToModel (weatherDTO: WeatherDTO):Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(),fact.temperature,fact.feelsLike,fact.condition,fact.icon))

}
