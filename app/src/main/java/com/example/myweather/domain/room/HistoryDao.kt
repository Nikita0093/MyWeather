package com.example.myweather.domain.room

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("INSERT INTO history_table (city,temperature,temperatureFeelLike,condition,icon ) VALUES (:city,:temperature,:temperatureFeelLike,:condition,:icon)")
    fun nativeInsert(
        city: String,
        temperature: Int,
        temperatureFeelLike: Int,
        condition: String = "cloudy",
        icon: String = "ovc_ra"
    )


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)


    @Delete
    fun delete(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Query("SELECT * FROM history_table")
    fun getAll(): List<HistoryEntity>


    @Query("SELECT * FROM history_table WHERE city LIKE :city")
    fun getHistoryForCity(city: String): List<HistoryEntity>


}