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
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

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
    var weatherForecast: ArrayList<ForecastDayInformation> = ArrayList<ForecastDayInformation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_weather_forecast, container, false)

        Log.i("oncreate","all is ok")

        //getForecastWeather()
        getForecastWeather = fragmentView.findViewById(R.id.updateButton)
        getForecastWeather.setOnClickListener {
            getForecastWeather()
        }

        if(Config.shouldUpdate){
            getForecastWeather()
        }

        //initList()

        return fragmentView

    }

    fun initList(){
        initRecyclerView()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    fun initRecyclerView(){
        Log.i("initRecyclerView","all is ok")

        recyclerViewAdapter = RecyclerViewAdapter(weatherForecast)
        recyclerView = fragmentView.findViewById<RecyclerView>(R.id.recyclerView).apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = recyclerViewAdapter
        }
    }

    fun getDay(addDays: Int):String{

        val aLocale = Locale.Builder().setLanguage("pl").setRegion("PL").build()

        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("EEEE")

        var mojaData: Date = Date()
        var dt: DateTime = DateTime(mojaData)

        var dayOfWeek = dt.plusDays(addDays).dayOfWeek().get()



        when(dayOfWeek){
            1 -> return "poniedzialek"
            2 -> return "wtorek"
            3 -> return "sroda"
            4 -> return "czwartek"
            5 -> return "piatek"
            6 -> return "sobota"
            7 -> return "niedziela"
        }

        return "nie ma takiego dnia"

    }


    fun getCelcius(kelvin: Double): Double{
        return kelvin - 274.15
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


                    weatherForecast.clear()

                    for(i in 0..4){

                        var temperature = getCelcius(weatherForecastResponse!!.list!![i]!!.temp!!.day)

                        var d = getDay(i+1)
                        var t = temperature.toString()
                        var h = weatherForecastResponse!!.list!![i]!!.humidity.toString()
                        var p = weatherForecastResponse!!.list!![i]!!.pressure.toString()
                        var c = weatherForecastResponse!!.list!![i]!!.clouds.toString()

                        var temp: ForecastDayInformation? = ForecastDayInformation(d,t,h,p,c)

                        weatherForecast.add(temp!!)

                    }

                    initList()
                    updateRecyclerView()


                } else {
                    Log.i("error",response.code().toString())
                    Log.i("response",response.body().toString())
                    Log.i("response body",response.errorBody().toString())
                }

            }

        })

    }

    fun updateRecyclerView(){
        var i = 0
        while(i<weatherForecast.size){
            recyclerViewAdapter.lista[i].temp = weatherForecast[i].temp
            recyclerViewAdapter.lista[i].pressure = weatherForecast[i].pressure
            recyclerViewAdapter.lista[i].clouds = weatherForecast[i].clouds
            recyclerViewAdapter.lista[i].humidity = weatherForecast[i].humidity
            recyclerViewAdapter.notifyItemInserted(i)
            i++
        }
    }

}
