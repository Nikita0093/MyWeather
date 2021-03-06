package com.example.myweather.view

import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.MyApp
import com.example.myweather.R
import com.example.myweather.lesson10.MapsFragment
import com.example.myweather.repository.ConnectionBroadcastReceiver
import com.example.myweather.repository.MyBroadcastReceiver
import com.example.myweather.utils.BroadcastReceiver_CONNECTION_WAVE
import com.example.myweather.utils.BroadcastReceiver_ERROR_WAVE
import com.example.myweather.view.historylist.HistoryWeatherListFragment
import com.example.myweather.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, WeatherListFragment.newInstance()).commit();
        }

        registerBroadcasting()
        MyApp.getHistoryDao().getAll()


    }

    private fun registerBroadcasting() {
        val receive = MyBroadcastReceiver()
        registerReceiver(receive, IntentFilter(BroadcastReceiver_ERROR_WAVE))

        val connectionReceiver = ConnectionBroadcastReceiver()
        registerReceiver(connectionReceiver, IntentFilter(BroadcastReceiver_CONNECTION_WAVE))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_historyBtn -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, HistoryWeatherListFragment.newInstance())
                    .addToBackStack("").commit()
            }

            R.id.menu_back -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, WeatherListFragment.newInstance()).commit()

            }

            R.id.menu_google_maps -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, MapsFragment()).commit()

            }
        }

        return super.onOptionsItemSelected(item)
    }

}