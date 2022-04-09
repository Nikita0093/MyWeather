package com.example.myweather.repository


import android.os.Parcelable
import com.example.myweather.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 10,
    val temperatureFeelLike: Int = 5
) :Parcelable

fun getDefaultCity() = City("Moscow")

@Parcelize
data class City(val name: String):Parcelable


fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("Лондон"), 1, 2),
        Weather(City("Токио"), 3, 4),
        Weather(City("Париж"), 5, 6),
        Weather(City("Берлин"), 7, 8),
        Weather(City("Рим"), 9, 10),
        Weather(City("Минск"), 11, 12),
        Weather(City("Стамбул"), 13, 14),
        Weather(City("Вашингтон"), 15, 16),
        Weather(City("Киев"), 17, 18),
        Weather(City("Пекин"), 19, 20)
    )
}

fun getRussianCities(): List<Weather> {
    return listOf(
        Weather(City("Москва"), 1, 2),
        Weather(City("Санкт-Петербург"), 3, 3),
        Weather(City("Новосибирск"), 5, 6),
        Weather(City("Екатеринбург"), 7, 8),
        Weather(City("Нижний Новгород"), 9, 10),
        Weather(City("Казань"), 11, 12),
        Weather(City("Челябинск"), 13, 14),
        Weather(City("Омск"), 15, 16),
        Weather(City("Ростов-на-Дону"), 17, 18),
        Weather(City("Уфа"), 19, 20)
    )
}
