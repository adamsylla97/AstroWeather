package com.example.astroweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {

    //GET for forecase weahter
    //api.openweathermap.org/data/2.5/forecast?lat=35&lon=139
    @GET("data/2.5/forecast/daily?")
    fun getForecastWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("cnt") cnt: String,
        @Query("APPID") app_id: String): Call<WeatherForecastResponse>

}