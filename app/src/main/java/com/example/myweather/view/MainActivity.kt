package com.example.myweather.view

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.R
import com.example.myweather.lesson4.*
import com.example.myweather.repository.MyBroadcastReceiver
import com.example.myweather.utils.BroadcastReceiver_ERROR_WAVE
import com.example.myweather.utils.BroadcastReceiver_WAVE
import com.example.myweather.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, WeatherListFragment.newInstance()).commit();
        }
        scopingFunctionsTest()

        val receive = MyBroadcastReceiver()
        registerReceiver(receive, IntentFilter(BroadcastReceiver_ERROR_WAVE))

    }

    private fun scopingFunctionsTest() {
        test1()
        test2()
        test3()
        test4()
        test5()
    }
}