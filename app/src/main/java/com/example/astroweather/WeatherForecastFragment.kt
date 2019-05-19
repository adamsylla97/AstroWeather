package com.example.astroweather


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

        Log.i("oncreate","all is ok")

        getForecastWeather = fragmentView.findViewById(R.id.updateButton)
        getForecastWeather.setOnClickListener {
            //getForecastWeather()
            updateRecyclerView()
        }

        initList()

        return fragmentView

    }

    fun initList(){
        Log.i("initList","all is ok")

        initRecyclerView()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    fun initRecyclerView(){
        Log.i("initRecyclerView","all is ok")
        recyclerView = fragmentView.findViewById(R.id.recyclerView)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(fragmentView.context)
    }

    fun updateRecyclerView(){
        recyclerViewAdapter.lista[2] = "asdasdasdasd"
        recyclerViewAdapter.notifyItemChanged(2)
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
                    val weatherForecastResponse: WeatherForecastResponse? = response.body()

                    Log.i("weather forecast", weatherForecastResponse!!.city!!.name)
                    Log.i("weather forecast", weatherForecastResponse!!.list!![0]!!.dt.toString())
                    Log.i("weather forecast", weatherForecastResponse!!.list!![1]!!.dt.toString())


                } else {
                    Log.i("error",response.code().toString())
                    Log.i("response",response.body().toString())
                    Log.i("response body",response.errorBody().toString())
                }

            }

        })

    }


}
