package com.example.myweather.view.historylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.myweather.R
import com.example.myweather.databinding.FragmentHistoryWeatherListRecycleBinding
import com.example.myweather.repository.Weather
import com.example.myweather.utils.KEY_BUNDLE_WEATHER
import com.example.myweather.view.MainActivity
import com.example.myweather.view.details.DetailsFragment

class HistoryWeatherListAdapter(private var data: List<Weather> = listOf()) :
    RecyclerView.Adapter<HistoryWeatherListAdapter.CityHolder>() {

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentHistoryWeatherListRecycleBinding.inflate(
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
            FragmentHistoryWeatherListRecycleBinding.bind(itemView).apply {
                historyCityName.text = weather.city.name
                historyTemperature.text = weather.temperature.toString()
                historyTemperatureFeelsLike.text = weather.temperatureFeelLike.toString()
                historyIcon.loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")


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

fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }.build()

    val request = ImageRequest.Builder(context)
        .crossfade(true)
        .crossfade(500)
        .data(url)
        .target(this)
        .build()
    imageLoader.enqueue(request)
}
