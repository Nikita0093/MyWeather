package com.example.myweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.databinding.ActivityMainWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivityWebView : AppCompatActivity() {
    lateinit var binding: ActivityMainWebviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnOk.setOnClickListener {

            Thread {
                var urlText = binding.editTextUrl.text.toString()
                val uri = URL(urlText)
                val urlConnection: HttpsURLConnection =
                    (uri.openConnection() as HttpsURLConnection).apply {
                        connectTimeout = 1000
                        readTimeout = 2000
                    }

                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = getLinesAsBigText(buffer)

                runOnUiThread {
                    binding.webView.loadUrl(urlText)
                }
            }.start()

        }


    }

    fun getLinesAsBigText(bufferedReader: BufferedReader): String {

        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }


}