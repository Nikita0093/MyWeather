package com.example.myweather.view.weatherlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myweather.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, WeatherListFragment.newInstance()).commit();
        }

    }
}