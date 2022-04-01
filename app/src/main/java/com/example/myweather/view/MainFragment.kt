package com.example.myweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.databinding.FragmentMainBinding
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        // return inflater.inflate(R.layout.fragment_main, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<AppState> { data -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        binding.buttonTest.setOnClickListener(View.OnClickListener { viewModel.getWeather() })
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Error -> {
                binding.textView.visibility = View.VISIBLE
                binding.loadingFrame.visibility = View.GONE
                // binding.textView.text = "$data"
                Snackbar.make(binding.textView, "$data", Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                binding.loadingFrame.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.textView.visibility = View.VISIBLE
                binding.loadingFrame.visibility = View.GONE
                // binding.textView.text = "Success"
                Snackbar.make(binding.textView, "Success", Snackbar.LENGTH_SHORT).show()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}