package com.example.myweather.view.weatherlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.R
import com.example.myweather.databinding.FragmentWeatherListBinding
import com.example.myweather.repository.Weather
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.ListViewModel
import com.google.android.material.snackbar.Snackbar


class WeatherListFragment : Fragment(), OnItemListClickListener {
    private var isRussian: Boolean = false
    private var _binding: FragmentWeatherListBinding? = null
    private val bindingListFragment get() = _binding!!

    private val adapter = WeatherListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return bindingListFragment.root


    }

    private fun saveData() {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("WeatherPos", isRussian)
        }.apply()

    }

    private fun loadData() {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", MODE_PRIVATE)
        val savedBoolean = sharedPreferences.getBoolean("WeatherPos", false)
        isRussian = savedBoolean

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingListFragment.recyclerView.adapter = adapter
        val viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        val observer = Observer<AppState> { data -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        loadData()
        if (!isRussian) {
            viewModel.getRussianWeather()
            bindingListFragment.floatActonButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_russia
                )

            )
        } else {
            viewModel.getWorldWeather()
            bindingListFragment.floatActonButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_world
                )
            )
        }
        bindingListFragment.floatActonButton.setOnClickListener(View.OnClickListener {
            loadData()
            if (isRussian) {
                isRussian = false
                saveData()
                viewModel.getRussianWeather()
                bindingListFragment.floatActonButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_russia
                    )

                )

            } else if (!isRussian) {
                isRussian = true
                saveData()
                viewModel.getWorldWeather()
                bindingListFragment.floatActonButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_world
                    )
                )
            }

        })

        setupFab()

    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireContext(), "Работает", Toast.LENGTH_LONG).show()
                getLocation()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_CONTACTS) -> {
                Toast.makeText(requireContext(), " Наверно Работает", Toast.LENGTH_LONG).show()
                //TODO...
            }
            else -> {
                Toast.makeText(requireContext(), "Не Работает", Toast.LENGTH_LONG).show()
                //TODO...
            }
        }
    }

    private fun setupFab() {
        bindingListFragment.mainFragmentFABLocation.setOnClickListener {
            checkPermission()
        }
    }

    val locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location) {

        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        context?.let {
            val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val providerGPS = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                providerGPS?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        10000L,
                        10.0f,
                        locationListener
                    )
                }
            }
        }

    }


    private fun renderData(data: AppState) {
        with(bindingListFragment) {
            when (data) {
                is AppState.Error -> {
                    loadingFrame.visibility = View.GONE
                    Snackbar.make(bindingListFragment.root, "$data", Snackbar.LENGTH_SHORT).show()
                }
                is AppState.Loading -> {
                    loadingFrame.visibility = View.VISIBLE
                    // binding.weatherCardView.visibility = View.INVISIBLE
                }
                is AppState.Success -> {
                    loadingFrame.visibility = View.GONE
                    adapter.setData(data.weatherList)

                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {

        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onItemClick(weather: Weather) {
        TODO("Not yet implemented")
    }
}
