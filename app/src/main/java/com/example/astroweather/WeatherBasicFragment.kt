package com.example.astroweather


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherBasicFragment : Fragment() {

    companion object {
        var baseUrl = "https://api.openweathermap.org/";
        var appId = "39b02c18d58d117fd575ddcb8c32b72d"
        var lat = 54.toString()//Config.latitude.toString();
        var lon = 123.toString()//Config.longitude.toString();
    }

    lateinit var viewFragment: View
    lateinit var actualTimeWeather: TextView
    lateinit var city: TextView
    lateinit var longitudeWeather: TextView
    lateinit var latitudeWeather: TextView
    lateinit var temperature: TextView
    lateinit var pressure: TextView
    lateinit var updateWeatherButton: Button
    lateinit var weatherIcon: ImageView

    fun initLayout(){

        actualTimeWeather = viewFragment.findViewById(R.id.actualTimeWeather)
        city = viewFragment.findViewById(R.id.city)
        longitudeWeather = viewFragment.findViewById(R.id.longitudeWeather)
        latitudeWeather = viewFragment.findViewById(R.id.latitudeWeather)
        temperature = viewFragment.findViewById(R.id.temperature)
        pressure = viewFragment.findViewById(R.id.pressure)
        updateWeatherButton = viewFragment.findViewById(R.id.updateWeatherButton)
        weatherIcon = viewFragment.findViewById(R.id.weatherIcon)
        updateWeatherButton.setOnClickListener {
            getCurrentWeather()
        }

    }

    fun getCelcius(kelvin: Float): Double{
        return kelvin - 274.15
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewFragment = inflater.inflate(R.layout.fragment_basic_weather, container, false)
        retainInstance = true

        initLayout()

        if(Config.shouldUpdate){
            getCurrentWeather()
            Log.i("WeatherBasicFragment","Hello world")
        } else {
            updateFromSharedPreferences()
        }

        return viewFragment
    }

    private fun updateFromSharedPreferences() {
        city.text = Config.city
        longitudeWeather.text = Config.longitude.toString()
        latitudeWeather.text = Config.latitude.toString()
        temperature.text = Config.temperature
        pressure.text = Config.pressure

        when(Config.weatherIcon){
            "01d" -> weatherIcon.setImageResource(R.drawable.android01d)
            "01n" -> weatherIcon.setImageResource(R.drawable.android01n)
            "02d" -> weatherIcon.setImageResource(R.drawable.android02d)
            "02n" -> weatherIcon.setImageResource(R.drawable.android02n)
            "03d" -> weatherIcon.setImageResource(R.drawable.android03d)
            "03n" -> weatherIcon.setImageResource(R.drawable.android03n)
            "04d" -> weatherIcon.setImageResource(R.drawable.android04d)
            "04n" -> weatherIcon.setImageResource(R.drawable.android04n)
            "09d" -> weatherIcon.setImageResource(R.drawable.android09d)
            "09n" -> weatherIcon.setImageResource(R.drawable.android09n)
            "10d" -> weatherIcon.setImageResource(R.drawable.android10d)
            "10n" -> weatherIcon.setImageResource(R.drawable.android10n)
            "11d" -> weatherIcon.setImageResource(R.drawable.android11d)
            "11n" -> weatherIcon.setImageResource(R.drawable.android11n)
            "13d" -> weatherIcon.setImageResource(R.drawable.android13d)
            "13n" -> weatherIcon.setImageResource(R.drawable.android13n)
            "50d" -> weatherIcon.setImageResource(R.drawable.android50d)
            "50n" -> weatherIcon.setImageResource(R.drawable.android50n)
        }

    }

    public fun getCurrentWeather() {

        var retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

        var weatherService: WeatherService = retrofit.create(WeatherService::class.java)
        var call = weatherService.getCurrentWeatherData(lat, lon, appId)
        call.enqueue(object : Callback<WeatherResponse>{
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                if(response.code() == 200){
                    val weatherResponse: WeatherResponse? = response.body() as WeatherResponse
                    assert(weatherResponse != null)
                    Log.i("astro Country",weatherResponse!!.sys!!.country)
                    Log.i("astro City",weatherResponse!!.name)
                    Log.i("astro Temp", weatherResponse.main!!.temp.toString())

                    city.text = weatherResponse!!.name
                    longitudeWeather.text = Config.longitude.toString()
                    latitudeWeather.text = Config.latitude.toString()
                    temperature.text = getCelcius(weatherResponse.main!!.temp).toString()
                    pressure.text = weatherResponse.main!!.pressure.toString()

                   // weatherIcon.setImageResource(R.drawable.android01d)

                    when(weatherResponse.weather!![0]!!.icon){
                        "01d" -> weatherIcon.setImageResource(R.drawable.android01d)
                        "01n" -> weatherIcon.setImageResource(R.drawable.android01n)
                        "02d" -> weatherIcon.setImageResource(R.drawable.android02d)
                        "02n" -> weatherIcon.setImageResource(R.drawable.android02n)
                        "03d" -> weatherIcon.setImageResource(R.drawable.android03d)
                        "03n" -> weatherIcon.setImageResource(R.drawable.android03n)
                        "04d" -> weatherIcon.setImageResource(R.drawable.android04d)
                        "04n" -> weatherIcon.setImageResource(R.drawable.android04n)
                        "09d" -> weatherIcon.setImageResource(R.drawable.android09d)
                        "09n" -> weatherIcon.setImageResource(R.drawable.android09n)
                        "10d" -> weatherIcon.setImageResource(R.drawable.android10d)
                        "10n" -> weatherIcon.setImageResource(R.drawable.android10n)
                        "11d" -> weatherIcon.setImageResource(R.drawable.android11d)
                        "11n" -> weatherIcon.setImageResource(R.drawable.android11n)
                        "13d" -> weatherIcon.setImageResource(R.drawable.android13d)
                        "13n" -> weatherIcon.setImageResource(R.drawable.android13n)
                        "50d" -> weatherIcon.setImageResource(R.drawable.android50d)
                        "50n" -> weatherIcon.setImageResource(R.drawable.android50n)
                    }


                } else {
                    Log.i("error",response.code().toString())
                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("no cos chyba poszlo","nie tak XD")
            }
        })



    }




}
