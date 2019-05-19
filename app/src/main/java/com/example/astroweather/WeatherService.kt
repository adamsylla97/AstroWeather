package com.example.astroweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService{

    //GET for actual weather
    //api.openweathermap.org/data/2.5/weather?lat=35&lon=139
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("APPID") app_id: String): Call<WeatherResponse>

}