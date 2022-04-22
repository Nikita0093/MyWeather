package com.example.myweather.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.databinding.FragmentWeatherListRecycleBinding
import com.example.myweather.repository.Weather
import com.example.myweather.utils.KEY_BUNDLE_WEATHER
import com.example.myweather.view.details.DetailsFragment

class WeatherListAdapter(private var data: List<Weather> = listOf()) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentWeatherListRecycleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size


    class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            FragmentWeatherListRecycleBinding.bind(itemView).apply {
                cityName.text = weather.city.name
                val bundle = Bundle()
                bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)

                root.setOnClickListener(View.OnClickListener {
                    (itemView.context as MainActivity).supportFragmentManager.beginTransaction()
                        .addToBackStack(" ").add(
                            R.id.mainContainer,
                            DetailsFragment.newInstance(bundle)
                        ).commit()
                })
            }
        }
    }
}
