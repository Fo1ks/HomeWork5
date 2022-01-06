package com.example.homework5.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CurrentWeatherResponse(
    val weather: List<WeatherItem>,
    val main: Main,
    @SerializedName("name") val cityName: String?,
    val sys: Sys,
    @SerializedName("dt") val date: LocalDateTime,
    val coord: Coord?
) {
    data class WeatherItem(
        val description: String,
        val icon: String
    )

    data class Main(
        val temp: Double,
        val humidity: Int
    )

    data class Sys(
        @SerializedName("country") val countryCode: String?
    )

    data class Coord(
        val lat: Double,
        val lon: Double
    )
}


data class ForecastResponse(
    val list: List<CurrentWeatherResponse>
)


data class HistoricalWeatherResponse(
    val current: Current,
) {
    data class Current(
        @SerializedName("dt") val date: LocalDateTime,
        val temp: Double,
        val humidity: Int,
        val weather: List<WeatherItems>
    ) {
        data class WeatherItems(
            val description: String,
            val icon: String
        )
    }

}

