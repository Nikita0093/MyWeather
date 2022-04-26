package com.example.myweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.R
import com.example.myweather.lesson4.*
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
    }

    private fun scopingFunctionsTest() {
        test1()
        test2()
        test3()
        test4()
        test5()
    }
}