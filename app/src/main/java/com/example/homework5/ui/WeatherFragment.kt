package com.example.homework5.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.homework5.data.WeatherRepository
import com.example.homework5.R
import com.example.homework5.data.CurrentWeather
import com.example.homework5.databinding.FragmentWeatherBinding

import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val repository = WeatherRepository()
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ForecastAdapter()
        binding.forecastList.adapter = adapter

        requireArguments().getParcelable<CurrentWeather>(SearchFragment.WEATHER_KEY)!!.let {
            binding.locationName.text = getString(R.string.location_name_template).format(it.cityName, it.countryCode)
            binding.weatherDescription.text = it.weatherDescription.replaceFirstChar { it.titlecase() }
            binding.temperature.text = getString(R.string.temperature_template).format(it.temperature)
            binding.humidity.text = getString(R.string.humidity_template).format(it.humidity)

            val url = "http://openweathermap.org/img/wn/${it.icon}@2x.png"
            Picasso.with(binding.root.context)
                .load(url)
                .into(binding.currentWeatherIcon)

            scope.launch {
                try {
                    adapter.updateForecast(repository.getForecast(it.cityName, it.countryCode))
                } catch (e: Exception) {
                    Log.w(javaClass.simpleName, e)
                    Snackbar.make(binding.root, R.string.not_found_message, Snackbar.LENGTH_SHORT).show()
                }

                binding.loader.isVisible = false
            }
        }
    }
}