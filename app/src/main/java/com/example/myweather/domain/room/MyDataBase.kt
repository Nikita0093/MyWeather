package com.example.myweather.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = arrayOf(HistoryEntity::class), version = 1 )
abstract class MyDataBase: RoomDatabase() {

    abstract fun historyDao():HistoryDao
}