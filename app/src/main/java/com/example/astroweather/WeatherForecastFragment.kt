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
import android.widget.Toast
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastFragment : Fragment() {

    lateinit var fragmentView: View

    companion object {
        var baseUrl = "https://api.openweathermap.org";
        var appId = "001b0f58045147663b1ea518d34d88b4"
        var cnt = "5"
        var lat = Config.latitudeWeahter.toString();
        var lon = Config.longitudeWeather.toString();
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

        update()

        Thread(Runnable {
            while (true){
                try{
                    if(activity != null){
                        activity!!.runOnUiThread {
                            if((!lon.equals(Config.longitudeWeather.toString()) || !lat.equals(Config.latitudeWeahter.toString())) && Config.invalidData == false){
                                lon = Config.longitudeWeather.toString()
                                lat = Config.latitudeWeahter.toString()
                                update()
                                if(fragmentView.context != null){
                                    Toast.makeText(fragmentView.context,"weather updated",Toast.LENGTH_LONG).show()
                                }
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

        //initList()

        return fragmentView

    }

    private fun update(){
        if(Config.shouldUpdate){
            getForecastWeather()
        } else {
            getForecastWeatherFromSharedPreferences()
        }
    }

    private fun getForecastWeatherFromSharedPreferences() {
//        var d = getDay(i+1)
//        var t = temperature.toString()
//        var h = weatherForecastResponse!!.list!![i]!!.humidity.toString()
//        var p = weatherForecastResponse!!.list!![i]!!.pressure.toString()
//        var c = weatherForecastResponse!!.list!![i]!!.clouds.toString()
//
//        var temp: ForecastDayInformation? = ForecastDayInformation(d,t,h,p,c)
//
//        weatherForecast.add(temp!!)

        weatherForecast.clear()

        //day1
        var d = Config.day1
        var t = Config.temp1
        var h = Config.humidity1
        var p = Config.humidity1
        var c = Config.clouds1

        var temp: ForecastDayInformation? = ForecastDayInformation(d!!, t!!, h!!, p!!, c!!)
        weatherForecast.add(temp!!)

        d = Config.day2
        t = Config.temp2
        h = Config.humidity2
        p = Config.humidity2
        c = Config.clouds2

        temp = ForecastDayInformation(d!!,t!!,h!!,p!!,c!!)
        weatherForecast.add(temp!!)

        d = Config.day3
        t = Config.temp3
        h = Config.humidity3
        p = Config.humidity3
        c = Config.clouds3

        temp = ForecastDayInformation(d!!,t!!,h!!,p!!,c!!)
        weatherForecast.add(temp!!)

        d = Config.day4
        t = Config.temp4
        h = Config.humidity4
        p = Config.humidity4
        c = Config.clouds4

        temp = ForecastDayInformation(d!!,t!!,h!!,p!!,c!!)
        weatherForecast.add(temp!!)

        d = Config.day5
        t = Config.temp5
        h = Config.humidity5
        p = Config.humidity5
        c = Config.clouds5

        temp = ForecastDayInformation(d!!,t!!,h!!,p!!,c!!)
        weatherForecast.add(temp!!)

        initList()
        updateRecyclerView()
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
                Log.i("no cos chyba poszlo","nie tak")
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
