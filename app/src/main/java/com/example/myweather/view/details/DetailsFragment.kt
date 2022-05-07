package com.example.myweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.repository.OnServerResponse
import com.example.myweather.repository.Weather
import com.example.myweather.repository.dto.WeatherDTO
import com.example.myweather.utils.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import kotlin.properties.Delegates

class DetailsFragment : Fragment(), OnServerResponse {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root


    }

    private lateinit var currentCityName: String
    private var currentCityImage by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather: Weather = requireArguments().getParcelable<Weather>(KEY_BUNDLE_WEATHER)!!

        currentCityName = weather.city.name
        currentCityImage = weather.city.imageId

        /*requireActivity().startService(
        Intent(
            requireContext(),
            DetailsFragmentService::class.java
        ).apply {
            putExtra(KEY_BUNDLE_LAT, weather.city.lat)
            putExtra(KEY_BUNDLE_LON, weather.city.lon)
        })
    requireActivity().registerReceiver(weatherReceiver, IntentFilter(BroadcastReceiver_WAVE))

         */
        getWeather(weather.city.lat, weather.city.lon)


    }

    private fun getWeather(coordinatesLat: Double, coordinatesLon: Double) {
        binding.loadingLayout.visibility = View.VISIBLE

        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(KEY_YANDEX_KEY, KEY_YANDEX_KEY_VALUE)
        builder.url("${KEY_YANDEX_DOMAIN_HARD}lat=$coordinatesLat&lon=$coordinatesLon")
        val request = builder.build()
        val answer: Callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                binding.loadingLayout.visibility = View.GONE
                //TODO ERROR
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val serverResponse = response.body()!!.string()
                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(serverResponse, WeatherDTO::class.java)
                    requireActivity().runOnUiThread {
                        renderData(weatherDTO)
                    }
                    //val message = Intent(BroadcastReceiver_WAVE)
                } else {
                    //TODO ERROR
                }
            }

        }

        val call = client.newCall(request)
        call.enqueue(answer)

    }

    private fun renderData(weather: WeatherDTO) {

        with(binding) {
            loadingLayout.visibility = View.GONE
            mainView.visibility = View.VISIBLE
            cityName.text = currentCityName
            temperatureValue.text = "${weather.factDTO.temperature}"
            feelsLikeValue.text = "${weather.factDTO.feelsLike}"
            cityCoordinates.text = "${weather.infoDTO.lat} ${weather.infoDTO.lon}"
            cityImage.setImageDrawable(resources.getDrawable(currentCityImage))
            weatherCondition.text = weather.factDTO.condition
            mainView.showSnackBar("Получилось")


        }

    }

    private fun View.showSnackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(this, text, duration).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireActivity().unregisterReceiver(weatherReceiver)
    }

    private val weatherReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                intent.getParcelableExtra<WeatherDTO>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER)?.let {
                    onResponse(it)

                }

            }


        }
    }


    companion object {

        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle
            return detailsFragment;

        }
    }

    override fun onResponse(weatherDTO: WeatherDTO) {
        renderData(weatherDTO)
    }


}
