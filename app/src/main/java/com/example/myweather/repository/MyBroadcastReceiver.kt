package com.example.myweather.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.myweather.utils.BroadcastReceiver_ERROR_CLIENT_BUNDLE
import com.example.myweather.utils.BroadcastReceiver_ERROR_SERVER_BUNDLE

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val messageServer = it.getStringExtra(BroadcastReceiver_ERROR_SERVER_BUNDLE)
            val messageClient = it.getStringExtra(BroadcastReceiver_ERROR_CLIENT_BUNDLE)

            if (messageClient != null && messageServer == null) {
                Toast.makeText(
                    context,
                    "Ошибка со стороны клиента: $messageClient",
                    Toast.LENGTH_LONG
                ).show()

            } else if (messageServer != null && messageClient == null) {
                Toast.makeText(
                    context,
                    "Ошибка со стороны сервера: $messageServer",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }
}
