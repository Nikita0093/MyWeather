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

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
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
                binding.textView.text = "$data"
            }
            is AppState.Loading -> {
                binding.loadingFrame.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.textView.visibility = View.VISIBLE
                binding.loadingFrame.visibility = View.GONE
                binding.textView.text = "Success"
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}