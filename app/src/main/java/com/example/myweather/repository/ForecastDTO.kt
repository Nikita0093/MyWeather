package com.example.myweather.repository


import com.google.gson.annotations.SerializedName

data class ForecastDTO(
    @SerializedName("date")
    val date: String,
    @SerializedName("date_ts")
    val dateTs: Double,
    @SerializedName("moon_code")
    val moonCode: Double,
    @SerializedName("moon_text")
    val moonText: String,
    @SerializedName("parts")
    val partDTOS: List<PartDTO>,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("week")
    val week: Double
)