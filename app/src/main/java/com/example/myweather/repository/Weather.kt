package com.example.myweather.repository


import android.os.Parcelable
import com.example.myweather.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 10,
    val temperatureFeelLike: Int = 5,
    val imageId: Int = 0
) : Parcelable

fun getDefaultCity() = City("Moscow")


@Parcelize
data class City(val name: String) : Parcelable


fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("Лондон"), 1, 2, R.drawable.london),
        Weather(City("Токио"), 3, 4, R.drawable.tokio),
        Weather(City("Париж"), 5, 6, R.drawable.paris),
        Weather(City("Берлин"), 7, 8, R.drawable.berlin),
        Weather(City("Рим"), 9, 10, R.drawable.rome),
        Weather(City("Минск"), 11, 12, R.drawable.minsk),
        Weather(City("Стамбул"), 13, 14, R.drawable.stambul),
        Weather(City("Вашингтон"), 15, 16, R.drawable.washington),
        Weather(City("Киев"), 17, 18, R.drawable.kiev),
        Weather(City("Пекин"), 19, 20, R.drawable.pekin)
    )
}

fun getRussianCities(): List<Weather> {
    return listOf(
        Weather(City("Москва"), 1, 2, R.drawable.moscow),
        Weather(City("Санкт-Петербург"), 3, 3, R.drawable.spb),
        Weather(City("Новосибирск"), 5, 6, R.drawable.novosibirsk),
        Weather(City("Екатеринбург"), 7, 8, R.drawable.ekaterinburg),
        Weather(City("Нижний Новгород"), 9, 10, R.drawable.nizhnij_nov),
        Weather(City("Казань"), 11, 12, R.drawable.kazan),
        Weather(City("Челябинск"), 13, 14, R.drawable.chelybinsk),
        Weather(City("Омск"), 15, 16, R.drawable.omsk),
        Weather(City("Ростов-на-Дону"), 17, 18, R.drawable.rostov_na_dony),
        Weather(City("Уфа"), 19, 20, R.drawable.ufa)
    )
}
