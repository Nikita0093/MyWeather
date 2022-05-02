package com.example.myweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.repository.OnServerResponse
import com.example.myweather.repository.Weather
import com.example.myweather.repository.WeatherDTO
import com.example.myweather.repository.WeatherLoader
import com.example.myweather.utils.KEY_BUNDLE_WEATHER
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
        Thread {
            WeatherLoader(this@DetailsFragment).loadWeather(weather.city.lat, weather.city.lon)

        }.start()


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
