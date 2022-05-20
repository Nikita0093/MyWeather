package com.example.myweather.view.weatherlist

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
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
import com.example.myweather.repository.City
import com.example.myweather.repository.Weather
import com.example.myweather.repository.getDefaultCity
import com.example.myweather.utils.KEY_BUNDLE_WEATHER
import com.example.myweather.view.details.DetailsFragment
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.ListViewModel
import com.google.android.material.snackbar.Snackbar


class WeatherListFragment : Fragment(), OnItemListClickListener {
    private var isRussian: Boolean = false
    private var _binding: FragmentWeatherListBinding? = null
    private val bindingListFragment get() = _binding!!

    private val adapter = WeatherListAdapter(this)


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

        setupFabLocation()

    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireContext(), "Работает", Toast.LENGTH_LONG).show()

                getLocation()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(requireContext(), " Наверно Работает", Toast.LENGTH_LONG).show()
                explain()
            }
            else -> {
                Toast.makeText(requireContext(), "Не Работает", Toast.LENGTH_LONG).show()
                requestPermission()
            }
        }
    }

    private val REQUEST_CODE = 999

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    explain()

                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun explain() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.dialog_rationale_title))
            .setMessage(resources.getString(R.string.dialog_rationale_message))
            .setPositiveButton(resources.getString(R.string.dialog_rationale_give_access)) { _, _ ->
                requestPermission()
            }
            .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }


    private fun setupFabLocation() {
        bindingListFragment.mainFragmentFABLocation.setOnClickListener {
            checkPermission()
        }
    }

    fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(requireContext())
        Thread {

            val addressText = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                10000
            )[0].getAddressLine(0)

            requireActivity().runOnUiThread {
                showWeatherAlertDialog(addressText, location)
            }

        }.start()


    }

    val locationListenerTime = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            getAddressByLocation(location)

        }

    }

    val locationListenerDistance = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            getAddressByLocation(location)

        }

    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        context?.let {
            val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val providerGPS = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                /*
                providerGPS?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        10000L,
                        0f,
                        locationListenerTime
                    )

                }

                    */

                providerGPS?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        100f,
                        locationListenerDistance
                    )
                }
            }
        }

    }

    private fun showWeatherAlertDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    onItemClick(
                        Weather(
                            City(
                                address,
                                location.latitude,
                                location.longitude,
                                getDefaultCity().imageId
                            )
                        )
                    )

                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()

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
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, DetailsFragment.newInstance(Bundle().apply {
                putParcelable(KEY_BUNDLE_WEATHER, weather)
            })).addToBackStack(" ").commit()

    }

}
