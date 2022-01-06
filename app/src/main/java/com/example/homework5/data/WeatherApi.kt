package com.example.homewok5.data

import com.example.homework5.data.CurrentWeatherResponse
import com.example.homework5.data.ForecastResponse
import com.example.homework5.data.HistoricalWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather?units=metric")
    suspend fun getWeather(
        @Query("q") location: String,
        @Query("appid") appId: String
    ): CurrentWeatherResponse

    @GET("forecast?units=metric")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("appid") appId: String
    ): ForecastResponse

    @GET("onecall/timemachine")
    suspend fun getHistoricalData(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("dt") date: Long,
        @Query("appid") appId: String
    ): HistoricalWeatherResponse
}