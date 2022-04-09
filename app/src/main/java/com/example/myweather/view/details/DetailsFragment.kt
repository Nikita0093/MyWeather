package com.example.myweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.repository.Weather
import com.example.myweather.utils.KEY_BUNDLE_WEATHER

class DetailsFragment : Fragment() {

    var _binding: FragmentDetailsBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        // return inflater.inflate(R.layout.fragment_main, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather: Weather = requireArguments().getParcelable<Weather>(KEY_BUNDLE_WEATHER)!!
        renderData(weather)


    }

    private fun renderData(weather: Weather) {
        binding.weatherCardView.visibility = View.VISIBLE
        binding.cityName.text = weather.city.name
        binding.temperature.text = "Temp:" + weather.temperature.toString() + "сº"
        binding.temperatureFeelLike.text =
            "FeelLike:" + weather.temperatureFeelLike.toString() + "сº"


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
