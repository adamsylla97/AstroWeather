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
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sun.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class WeatherBasicFragment : Fragment() {

    companion object {
        var baseUrl = "https://api.openweathermap.org/";
        var appId = "39b02c18d58d117fd575ddcb8c32b72d"
        var lat: String = Config.latitude.toString();
        var lon: String = Config.longitude.toString();
        var userValue = Config.userUpdate
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

    lateinit var userUpdateBasic: TextView

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

        userUpdateBasic = viewFragment.findViewById(R.id.userUpdateBasic)
    }

    fun getCelcius(kelvin: Float): Double{
        return kelvin - 274.15
    }

    fun update(){
        if(Config.shouldUpdate){
            getCurrentWeather()
        } else {
            updateFromSharedPreferences()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewFragment = inflater.inflate(R.layout.fragment_basic_weather, container, false)
        retainInstance = true

        initLayout()

        update()

        //clock part
        val sdf = SimpleDateFormat("HH:mm:ss")

        Thread(Runnable {
            while (true){
                var currentDate = sdf.format(Date())
                currentDate = sdf.format(Date())

                try{
                    if(activity != null){
                        activity!!.runOnUiThread {
                            if(actualTimeWeather!=null)
                                actualTimeWeather.text = currentDate.toString()

                            if((!lon.equals(Config.longitude.toString()) || !lat.equals(Config.latitude.toString())) && Config.invalidData == false){
                                lon = Config.latitude.toString()
                                lat = Config.longitude.toString()
                                update()
                                if(viewFragment.context != null){
                                    Toast.makeText(viewFragment.context,"weather updated",Toast.LENGTH_LONG).show()
                                }
                            }
                            if(userValue != Config.userUpdate){
                                update()
                                userUpdateBasic.text = "ccc"
                                userValue = Config.userUpdate
                            }

                        }
                    }
                } catch (e: Exception){
                    if(activity != null){
                        activity!!.finish()
                    }
                }

                Thread.sleep(1000)

            }
        }).start()

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
