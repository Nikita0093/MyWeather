package com.example.myweather.view.historylist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.databinding.FragmentHistoryWeatherListBinding
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryWeatherListFragment : Fragment() {

    private var _binding: FragmentHistoryWeatherListBinding? = null
    private val bindingListFragment get() = _binding!!

    private val adapter = HistoryWeatherListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryWeatherListBinding.inflate(inflater, container, false)
        return bindingListFragment.root

    }

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)

    }


    var isRussian: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingListFragment.recyclerView.adapter = adapter
        val observer = Observer<AppState> { data -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getAll()


    }

    private fun renderData(data: AppState) {
        with(bindingListFragment) {
            when (data) {
                is AppState.Error -> {
                    Snackbar.make(bindingListFragment.root, "$data", Snackbar.LENGTH_SHORT).show()
                }
                is AppState.Loading -> {

                }
                is AppState.Success -> {

                    adapter.setData(data.weatherList)

                }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryWeatherListFragment()
    }
}
