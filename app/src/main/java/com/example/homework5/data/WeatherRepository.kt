package com.example.homework5.data

import com.example.homewok5.data.WeatherApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class WeatherRepository {
    companion object {
        val api = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().registerTypeAdapter(
                        LocalDateTime::class.java,
                        JsonDeserializer { json, _, _ ->
                            Instant.ofEpochSecond(json.asJsonPrimitive.asLong)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                        }
                    ).create()
                )
            )
            .build()
            .create(WeatherApi::class.java)


        val appId = "790afb7d0a5bedea9205be3e59b92ae7" // own token
    }

    private fun CurrentWeatherResponse.toCurrentWeather() = CurrentWeather(
        cityName = cityName.orEmpty(),
        countryCode = sys.countryCode.orEmpty(),
        weatherDescription = weather.first().description,
        temperature = main.temp,
        humidity = main.humidity,
        dateTime = date,
        icon = weather.first().icon,
        lat = coord?.lat,
        lon = coord?.lon
    )

    private fun HistoricalWeatherResponse.toHistoricalWeather() = HistoricalWeather(
        weatherDescription = current.weather.first().description,
        temperature = current.temp,
        humidity = current.humidity,
        dateTime = current.date,
        icon = current.weather.first().icon
    )

    suspend fun getCurrentWeather(city: String, countryCode: String): CurrentWeather =
        withContext(Dispatchers.IO) {
            api.getWeather("$city, $countryCode", appId).toCurrentWeather()
        }

    suspend fun getForecast(city: String?, countryCode: String?): List<CurrentWeather> =
        withContext(Dispatchers.IO) {
            api.getForecast("$city, $countryCode", appId).list.map { it.toCurrentWeather() }
        }

    suspend fun getHistoricalWeather(latitude: Double?, longitude: Double?, startDateTime: LocalDateTime): List<HistoricalWeather> =
        withContext(Dispatchers.IO) {
            List(10)
            { (it + 1) * 3 }
                .map { startDateTime.minusHours(it.toLong()) }
                .map {
                    val seconds = it.atZone(ZoneId.systemDefault()).toEpochSecond()
                    async {
                        api.getHistoricalData(latitude, longitude, seconds, appId)
                    }
                }
                .awaitAll()
                .map { it.toHistoricalWeather()  }
        }
}