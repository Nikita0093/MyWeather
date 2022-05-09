package com.example.myweather.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Int,
    val temperatureFeelLike: Int,
    val condition: String = "cloudy",
    val icon: String = "ovc_ra"
)

