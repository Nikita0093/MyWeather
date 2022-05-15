package com.example.myweather.view.weatherlist

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val savedBoolean = sharedPreferences.getBoolean("WeatherPos", true)
        isRussian = savedBoolean

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingListFragment.recyclerView.adapter = adapter
        val viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        val observer = Observer<AppState> { data -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        loadData()
        if (isRussian == false) {
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
            if (isRussian == true) {
                isRussian = false
                saveData()
                viewModel.getRussianWeather()
                bindingListFragment.floatActonButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_russia
                    )

                )

            } else if (isRussian == false) {
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
