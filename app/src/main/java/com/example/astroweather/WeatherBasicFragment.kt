package com.example.astroweather


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherBasicFragment : Fragment() {

    companion object {
        var baseUrl = "http://api.openweathermap.org/";
        var appId = "39b02c18d58d117fd575ddcb8c32b72d"
        var lat = 23.toString()//Config.latitude.toString();
        var lon = 23.toString()//Config.longitude.toString();
    }

    lateinit var viewFragment: View
    lateinit var actualTimeWeather: TextView
    lateinit var city: TextView
    lateinit var longitudeWeather: TextView
    lateinit var latitudeWeather: TextView
    lateinit var temperature: TextView
    lateinit var pressure: TextView
    lateinit var updateWeatherButton: Button

    fun initLayout(){

        actualTimeWeather = viewFragment.findViewById(R.id.actualTimeWeather)
        city = viewFragment.findViewById(R.id.city)
        longitudeWeather = viewFragment.findViewById(R.id.longitudeWeather)
        latitudeWeather = viewFragment.findViewById(R.id.latitudeWeather)
        temperature = viewFragment.findViewById(R.id.temperature)
        pressure = viewFragment.findViewById(R.id.pressure)
        updateWeatherButton = viewFragment.findViewById(R.id.updateWeatherButton)
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

        initLayout()

        return viewFragment
    }

    fun getCurrentWeather() {

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
