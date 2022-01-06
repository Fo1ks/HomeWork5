package com.example.homework5.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.homework5.data.WeatherRepository
import com.example.homework5.R
import com.example.homework5.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val scope = MainScope()
    private val repository = WeatherRepository()

    companion object {
        const val WEATHER_KEY = "weather"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchButton.setOnClickListener {
            val city = binding.cityInput.text.toString()
            val countryCode = binding.countryCodeInput.text.toString()

            if (city.isNotBlank() && countryCode.isNotBlank()) {
                scope.launch {
                    binding.searchButton.isVisible = false
                    binding.loader.isVisible = true
                    try {
                        val currentWeather = repository.getCurrentWeather(city, countryCode)
                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragmentContainer,
                                WeatherFragment().also {
                                    it.arguments = Bundle().apply {
                                        putParcelable(WEATHER_KEY, currentWeather)
                                    }
                                }
                            )
                            .addToBackStack(null)
                            .commit()

                    } catch (e: Exception) {
                        Log.w(javaClass.simpleName, e)
                        Snackbar.make(binding.root, R.string.not_found_message, Snackbar.LENGTH_SHORT).show()
                    }

                    binding.loader.isVisible = false
                    binding.searchButton.isVisible = true
                }
            } else {
                val errorMessage = getString(R.string.invalid_input_massage)
                if (city.isBlank()) binding.cityInputLayout.error = errorMessage
                if (countryCode.isBlank())binding.countryCodeInputLayout.error = errorMessage
            }
        }

        binding.searchButtonHistorical.setOnClickListener {
            val city = binding.cityInput.text.toString()
            val countryCode = binding.countryCodeInput.text.toString()

            if (city.isNotBlank() && countryCode.isNotBlank()) {
                scope.launch {
                    binding.searchButtonHistorical.isVisible = false
                    binding.loader.isVisible = true
                    try {
                        val currentWeather = repository.getCurrentWeather(city, countryCode)
                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragmentContainer,
                                HistoricalWeatherFragment().also {
                                    it.arguments = Bundle().apply {
                                        putParcelable(WEATHER_KEY, currentWeather)
                                    }
                                }
                            )
                            .addToBackStack(null)
                            .commit()

                    } catch (e: Exception) {
                        Log.w(javaClass.simpleName, e)
                        Snackbar.make(binding.root, R.string.not_found_message, Snackbar.LENGTH_SHORT).show()
                    }

                    binding.loader.isVisible = false
                    binding.searchButtonHistorical.isVisible = true
                }
            } else {
                val errorMessage = getString(R.string.invalid_input_massage)
                if (city.isBlank()) binding.cityInputLayout.error = errorMessage
                if (countryCode.isBlank())binding.countryCodeInputLayout.error = errorMessage
            }
        }

        binding.cityInput.addTextChangedListener {
            binding.cityInputLayout.isErrorEnabled = false
        }
        binding.countryCodeInput.addTextChangedListener {
            binding.countryCodeInputLayout.isErrorEnabled = false
        }
    }
}