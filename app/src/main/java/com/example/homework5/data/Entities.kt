package com.example.homework5.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class CurrentWeather(
    val cityName: String?,
    val countryCode: String?,
    val weatherDescription: String,
    val temperature: Double,
    val humidity: Int?,
    val dateTime: LocalDateTime,
    val icon: String,
    val lon: Double?,
    val lat: Double?,
) : Parcelable

@Parcelize
data class HistoricalWeather(
    val weatherDescription: String,
    val temperature: Double,
    val humidity: Int,
    val dateTime: LocalDateTime,
    val icon: String,
) : Parcelable