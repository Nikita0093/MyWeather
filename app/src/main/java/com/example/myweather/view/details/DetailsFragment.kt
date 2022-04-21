package com.example.myweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.repository.Weather
import com.example.myweather.utils.KEY_BUNDLE_WEATHER
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


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
        with(binding) {
            weatherCardView.visibility = View.VISIBLE
            cityName.text = weather.city.name
            temperature.text = "Температура:" + weather.temperature.toString() + "º"
            temperatureFeelLike.text =
                "Ощущается как:" + weather.temperatureFeelLike.toString() + "º"
            cityImage.setImageDrawable(resources.getDrawable(weather.imageId))

            mainViewFragment.showSnackBar()

        }


    }

    fun View.showSnackBar() {
        Snackbar.make(this, "Получилось", Snackbar.LENGTH_SHORT).show()

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
