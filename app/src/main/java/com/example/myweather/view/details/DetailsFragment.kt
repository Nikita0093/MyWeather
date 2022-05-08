package com.example.myweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.repository.Weather
import com.example.myweather.utils.KEY_BUNDLE_WEATHER
import com.example.myweather.viewmodel.DetailsState
import com.example.myweather.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*
import kotlin.properties.Delegates

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)

    }

    private lateinit var currentCityName: String
    private var currentCityImage by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<DetailsState> {
            override fun onChanged(t: DetailsState) {
                renderData(t)
            }
        })
        val weather: Weather = requireArguments().getParcelable<Weather>(KEY_BUNDLE_WEATHER)!!
        currentCityImage = weather.city.imageId

        viewModel.getWeather(weather.city)


    }


    private fun renderData(detailsState: DetailsState) {
        when (detailsState) {
            is DetailsState.Error -> {
                val error = detailsState.error
                mainView.showSnackBar("Ошибка: $error")

            }

            DetailsState.Loading -> {

            }
            is DetailsState.Success -> {

                val weather = detailsState.weather
                with(binding) {
                    loadingLayout.visibility = View.GONE
                    mainView.visibility = View.VISIBLE
                    cityName.text = weather.city.name
                    temperatureValue.text = "${weather.temperature}"
                    feelsLikeValue.text = "${weather.temperatureFeelLike}"
                    cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"
                    cityImage.setImageDrawable(resources.getDrawable(currentCityImage))

                   //weatherIcon.load("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")

                    mainView.showSnackBar("Получилось")
                }

            }
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

}
