package com.example.myweather.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.myweather.utils.BroadcastReceiver_CONNECTION_BUNDLE

class ConnectionBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        var noConnectivity =
            intent?.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
        if (noConnectivity == false) {
            intent?.putExtra(BroadcastReceiver_CONNECTION_BUNDLE, false)

        } else {
            intent?.putExtra(BroadcastReceiver_CONNECTION_BUNDLE, true)

        }
    }
}


