package com.example.myweather.view.weatherlist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.R
import com.example.myweather.lesson4.*
import kotlinx.android.synthetic.main.activity_main.*

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