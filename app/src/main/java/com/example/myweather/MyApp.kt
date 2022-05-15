package com.example.myweather

import android.app.Application
import androidx.room.Room
import com.example.myweather.domain.room.HistoryDao
import com.example.myweather.domain.room.MyDataBase
import kotlin.concurrent.thread

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private var database: MyDataBase? = null
        private var appContext: MyApp? = null

        fun getHistoryDao(): HistoryDao {
            if (database == null) {
                if (appContext != null) {


                    database = Room.databaseBuilder(appContext!!, MyDataBase::class.java, "test").allowMainThreadQueries().build()


                } else {
                    throw IllegalStateException("Что то пошло не так")
                }

            }
            return database!!.historyDao()
        }
    }
}

