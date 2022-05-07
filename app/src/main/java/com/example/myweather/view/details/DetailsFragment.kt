package com.example.myweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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

        requireActivity().startService(
            Intent(
                requireContext(),
                DetailsFragmentService::class.java
            ).apply {
                putExtra(KEY_BUNDLE_LAT, weather.city.lat)
                putExtra(KEY_BUNDLE_LON, weather.city.lon)
            })
        requireActivity().registerReceiver(weatherReceiver, IntentFilter(BroadcastReceiver_WAVE))


    }

    private fun renderData(weather: WeatherDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            cityName.text = currentCityName
            temperatureValue.text = "${weather.factDTO.temperature}"
            feelsLikeValue.text = "${weather.factDTO.feelsLike}"
            cityCoordinates.text = "${weather.infoDTO.lat} ${weather.infoDTO.lon}"
            cityImage.setImageDrawable(resources.getDrawable(currentCityImage))
            weatherCondition.text = weather.factDTO.condition
            //mainView.showSnackBar("Получилось")


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
