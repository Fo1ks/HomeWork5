package com.example.homework5.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.R
import com.example.homework5.data.CurrentWeather
import com.example.homework5.data.HistoricalWeather
import com.example.homework5.databinding.ItemForecastBinding
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    private var forecast: List<CurrentWeather> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateForecast(forecast: List<CurrentWeather>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecast[position])
    }

    override fun getItemCount() = forecast.size

    class ForecastViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)//DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        }

        fun bind(weather: CurrentWeather) {
            binding.dateTime.text = ((weather.dateTime).toString()).format(formatter)
            binding.itemWeatherDescription.text = weather.weatherDescription?.replaceFirstChar { it.titlecase() }
            binding.itemTemperature.text = binding.root
                .context.getString(R.string.temperature_template)
                .format(weather.temperature)
            binding.itemHumidity.text = binding.root
                .context
                .getString(R.string.humidity_template)
                .format(weather.humidity)

            val url = "http://openweathermap.org/img/wn/${weather.icon}@2x.png"

            Picasso.with(binding.root.context)
                .load(url)
                .into(binding.weatherIcon)

        }
    }
}

class HistoricalAdapter : RecyclerView.Adapter<HistoricalAdapter.HistoricalViewHolder>() {
    private var forecast: List<HistoricalWeather> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateHistorical(forecast: List<HistoricalWeather>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalViewHolder {
        return HistoricalViewHolder(
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoricalViewHolder, position: Int) {
        holder.bind(forecast[position])
    }

    override fun getItemCount() = forecast.size

    class HistoricalViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)//DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        }

        fun bind(weather: HistoricalWeather) {
            binding.dateTime.text = ((weather.dateTime).toString()).format(formatter)
            binding.itemWeatherDescription.text = weather.weatherDescription.replaceFirstChar { it.titlecase() }
            binding.itemTemperature.text = binding.root
                .context.getString(R.string.temperature_template)
                .format(weather.temperature)
            binding.itemHumidity.text = binding.root
                .context
                .getString(R.string.humidity_template)
                .format(weather.humidity)

            val url = "http://openweathermap.org/img/wn/${weather.icon}@2x.png"

            Picasso.with(binding.root.context)
                .load(url)
                .into(binding.weatherIcon)

        }
    }
}