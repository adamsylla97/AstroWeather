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

class WeatherAdditionalFragment : Fragment() {

    companion object {
        var baseUrl = "https://api.openweathermap.org/";
        var appId = "39b02c18d58d117fd575ddcb8c32b72d"
        var lat = 23.toString();
        var lon = 23.toString();
    }

    lateinit var viewFragment: View

    lateinit var tempButton: Button
    lateinit var sky: TextView
    lateinit var humidity: TextView
    lateinit var clouds: TextView
    lateinit var wind: TextView

    fun initLayout(){

        sky = viewFragment.findViewById(R.id.sky)
        humidity = viewFragment.findViewById(R.id.humidity)
        clouds = viewFragment.findViewById(R.id.clouds)
        wind = viewFragment.findViewById(R.id.wind)

        tempButton = viewFragment.findViewById(R.id.tempUpdate)
        tempButton.setOnClickListener {
            getCurrentWeather()
        }
    }

    private fun updateFromSharedPreferences() {
        wind.text = Config.windSpeed
        sky.text = Config.sky
        humidity.text = Config.humidity
        clouds.text = Config.clouds
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewFragment = inflater.inflate(R.layout.fragment_weather_additional, container, false)

        initLayout()
        if(Config.shouldUpdate){
            getCurrentWeather()
        } else {
            updateFromSharedPreferences()
        }

        return viewFragment
    }



    fun getCurrentWeather() {

        var retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

        var weatherService: WeatherService = retrofit.create(WeatherService::class.java)
        var call = weatherService.getCurrentWeatherData(
            lat,
            lon,
            appId
        )
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                if(response.code() == 200){
                    val weatherResponse: WeatherResponse? = response.body() as WeatherResponse
                    assert(weatherResponse != null)

                    Log.i("addit wind speed",weatherResponse!!.wind!!.speed.toString())
                    Log.i("addit wind deg",weatherResponse!!.wind!!.deg.toString())
                    Log.i("addit humidity",weatherResponse!!.main!!.humidity.toString())
                    Log.i("addit clouds",weatherResponse!!.clouds!!.all.toString())
                    Log.i("addit weather desc",weatherResponse!!.weather!![0].description)
                    Log.i("addit weather icon",weatherResponse!!.weather!![0].icon)

                    wind.text = weatherResponse!!.wind!!.speed.toString()
                    sky.text = weatherResponse!!.weather!![0].description
                    humidity.text = weatherResponse!!.main!!.humidity.toString()
                    clouds.text = weatherResponse!!.clouds!!.all.toString()

                } else {
                    Log.i("error",response.code().toString())
                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("no cos chyba poszlo","nie tak XD")
            }
        })

    }


}
