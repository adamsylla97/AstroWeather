package com.example.astroweather


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.gson.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherForecastFragment : Fragment() {

    lateinit var fragmentView: View

    companion object {
        var baseUrl = "https://api.openweathermap.org";
        var appId = "001b0f58045147663b1ea518d34d88b4"
        var cnt = "5"
        var lat = 23.toString()//Config.latitude.toString();
        var lon = 23.toString()//Config.longitude.toString();
    }

    lateinit var getForecastWeather: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_weather_forecast, container, false)

        getForecastWeather = fragmentView.findViewById(R.id.getForecastWeather)
        getForecastWeather.setOnClickListener {
            getForecastWeather()
        }

        return fragmentView

    }

    fun getForecastWeather() {

        var retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

        var weatherForecastService: WeatherForecastService = retrofit.create(WeatherForecastService::class.java)
        var call = weatherForecastService.getForecastWeatherData(
            lat,
            lon,
            cnt,
            appId
        )

        call.enqueue(object : Callback<WeatherForecastResponse> {
            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                Log.i("no cos chyba poszlo","nie tak XD")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {

                if(response.code() == 200){
                    //val weatherResponse: WeatherResponse? = response.body() as WeatherResponse
                    //val weatherResponseList: List<WeatherResponse> = response.body() as List<WeatherResponse>
                    //assert(weatherResponse != null)
                    //val weatherResponseString: String = response.body().toString()
                    val weatherForecastResponse: WeatherForecastResponse? = response.body()

                    Log.i("weather forecast", weatherForecastResponse!!.city!!.name)
                    Log.i("weather forecast", weatherForecastResponse!!.list!![0]!!.dt.toString())
                    Log.i("weather forecast", weatherForecastResponse!!.list!![1]!!.dt.toString())

                    //Log.i("cos pobralem",weatherResponse!!.name)
                    //Log.i("lista:",weatherResponseList.size.toString())


                } else {
                    Log.i("error",response.code().toString())
                    Log.i("response",response.body().toString())
                    Log.i("response body",response.errorBody().toString())
                }

            }

        })

    }


}
