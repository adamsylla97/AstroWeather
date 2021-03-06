package com.example.astroweather

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {

        var sharedPreferences: SharedPreferences? = null
        val PREFS_FILENAME = "myprefs"

        fun saveToSharedPreferences(){
            var editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            var gson: Gson = Gson()
            var json: String = gson.toJson(Config.favCities)
            Log.i("favciries",json)
            editor.putString("favcities",json)
            editor.apply()
        }

        fun loadFromSharedPreferences(){
            var gson: Gson = Gson()
            var json: String? = sharedPreferences!!.getString("favcities",null)
            if(json!=null){
                val type = object : TypeToken<ArrayList<CityData>>() {}.type
                var listToConfig: List<CityData> = gson.fromJson(json,type)
                Log.i("favci",listToConfig[1].toString())
                Config.favCities = listToConfig
            }
        }

        fun getDataFromSharedPreferences(){
            //basic weather
            Config.city = sharedPreferences!!.getString("city","null")
            Config.temperature = sharedPreferences!!.getString("temperature","null")
            Config.weatherIcon = sharedPreferences!!.getString("weatherIcon","null")
            Config.pressure = sharedPreferences!!.getString("pressure","null")

            //additional weather
            Config.windSpeed = sharedPreferences!!.getString("windSpeed","null")
            Config.humidity = sharedPreferences!!.getString("humidity","null")
            Config.clouds = sharedPreferences!!.getString("clouds","null")
            Config.sky = sharedPreferences!!.getString("sky","null")

            //forecast weather day1
            Config.day1 = sharedPreferences!!.getString("day1","null")
            Config.temp1 =  sharedPreferences!!.getString("temp1","null")
            Config.pressure1 = sharedPreferences!!.getString("pressure1","null")
            Config.humidity1 = sharedPreferences!!.getString("humidity1","null")
            Config.clouds1 = sharedPreferences!!.getString("clouds1","null")

            //forecast weather day2
            Config.day2 = sharedPreferences!!.getString("day2","null")
            Config.temp2 =  sharedPreferences!!.getString("temp2","null")
            Config.pressure2 = sharedPreferences!!.getString("pressure2","null")
            Config.humidity2 = sharedPreferences!!.getString("humidity2","null")
            Config.clouds2 = sharedPreferences!!.getString("clouds2","null")

            //forecast weather day3
            Config.day3 = sharedPreferences!!.getString("day3","null")
            Config.temp3 =  sharedPreferences!!.getString("temp3","null")
            Config.pressure3 = sharedPreferences!!.getString("pressure3","null")
            Config.humidity3 = sharedPreferences!!.getString("humidity3","null")
            Config.clouds3 = sharedPreferences!!.getString("clouds3","null")

            //forecast weather day4
            Config.day4 = sharedPreferences!!.getString("day1","null")
            Config.temp4 =  sharedPreferences!!.getString("temp1","null")
            Config.pressure4 = sharedPreferences!!.getString("pressure1","null")
            Config.humidity4 = sharedPreferences!!.getString("humidity1","null")
            Config.clouds4 = sharedPreferences!!.getString("clouds1","null")

            //forecast weather day5
            Config.day5 = sharedPreferences!!.getString("day1","null")
            Config.temp5 =  sharedPreferences!!.getString("temp1","null")
            Config.pressure5 = sharedPreferences!!.getString("pressure1","null")
            Config.humidity5 = sharedPreferences!!.getString("humidity1","null")
            Config.clouds5 = sharedPreferences!!.getString("clouds1","null")

        }

        fun getCelcius(kelvin: Float): Double{
            return kelvin - 274.15
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

        fun setupSharedPreferencesForForecastWeather(){
            var retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WeatherForecastFragment.baseUrl)
                .build()

            var weatherForecastService: WeatherForecastService = retrofit.create(WeatherForecastService::class.java)
            var call = weatherForecastService.getForecastWeatherData(
                WeatherForecastFragment.lat,
                WeatherForecastFragment.lon,
                WeatherForecastFragment.cnt,
                WeatherForecastFragment.appId
            )

            call.enqueue(object : Callback<WeatherForecastResponse> {
                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    Log.i("no cos chyba poszlo","nie tak")
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {

                    if(response.code() == 200){
                        val weatherForecastResponse: WeatherForecastResponse? = response.body()

                        val editor = sharedPreferences!!.edit()

                        Config.day1 = getDay(1)
                        if(Config.units){
                            Config.temp1 = getCelcius(weatherForecastResponse!!.list!![0]!!.temp!!.day).toString()
                        } else {
                            Config.temp1 = weatherForecastResponse!!.list!![0]!!.temp!!.day.toString()
                        }
                        Config.pressure1 = weatherForecastResponse!!.list!![0]!!.humidity.toString()
                        Config.humidity1 = weatherForecastResponse!!.list!![0]!!.pressure.toString()
                        Config.clouds1 = weatherForecastResponse!!.list!![0]!!.clouds.toString()

                        editor.putString("day1",Config.day1)
                        editor.putString("temp1",Config.temp1)
                        editor.putString("pressure1",Config.pressure1)
                        editor.putString("humidity1",Config.humidity1)
                        editor.putString("clouds1",Config.clouds1)

                        Config.day2 = getDay(2)
                        if(Config.units){
                            Config.temp2 = getCelcius(weatherForecastResponse!!.list!![1]!!.temp!!.day).toString()
                        } else {
                            Config.temp2 = weatherForecastResponse!!.list!![1]!!.temp!!.day.toString()
                        }
                        Config.pressure2 = weatherForecastResponse!!.list!![1]!!.humidity.toString()
                        Config.humidity2 = weatherForecastResponse!!.list!![1]!!.pressure.toString()
                        Config.clouds2 = weatherForecastResponse!!.list!![1]!!.clouds.toString()

                        editor.putString("day2",Config.day2)
                        editor.putString("temp2",Config.temp2)
                        editor.putString("pressure2",Config.pressure2)
                        editor.putString("humidity2",Config.humidity2)
                        editor.putString("clouds2",Config.clouds2)

                        Config.day3 = getDay(3)
                        if(Config.units){
                            Config.temp3 = getCelcius(weatherForecastResponse!!.list!![2]!!.temp!!.day).toString()
                        } else {
                            Config.temp3 = weatherForecastResponse!!.list!![2]!!.temp!!.day.toString()
                        }
                        Config.pressure3 = weatherForecastResponse!!.list!![2]!!.humidity.toString()
                        Config.humidity3 = weatherForecastResponse!!.list!![2]!!.pressure.toString()
                        Config.clouds3 = weatherForecastResponse!!.list!![2]!!.clouds.toString()

                        editor.putString("day3",Config.day3)
                        editor.putString("temp3",Config.temp3)
                        editor.putString("pressure3",Config.pressure3)
                        editor.putString("humidity3",Config.humidity3)
                        editor.putString("clouds3",Config.clouds3)

                        Config.day4 = getDay(4)
                        if(Config.units){
                            Config.temp4 = getCelcius(weatherForecastResponse!!.list!![3]!!.temp!!.day).toString()
                        } else {
                            Config.temp4 = weatherForecastResponse!!.list!![3]!!.temp!!.day.toString()
                        }
                        Config.pressure4 = weatherForecastResponse!!.list!![3]!!.humidity.toString()
                        Config.humidity4 = weatherForecastResponse!!.list!![3]!!.pressure.toString()
                        Config.clouds4 = weatherForecastResponse!!.list!![3]!!.clouds.toString()

                        editor.putString("day4",Config.day4)
                        editor.putString("temp4",Config.temp4)
                        editor.putString("pressure4",Config.pressure4)
                        editor.putString("humidity4",Config.humidity4)
                        editor.putString("clouds4",Config.clouds4)

                        Config.day5 = getDay(5)
                        if(Config.units){
                            Config.temp5 = getCelcius(weatherForecastResponse!!.list!![4]!!.temp!!.day).toString()
                        } else {
                            Config.temp5 = weatherForecastResponse!!.list!![4]!!.temp!!.day.toString()
                        }
                        Config.pressure5 = weatherForecastResponse!!.list!![4]!!.humidity.toString()
                        Config.humidity5 = weatherForecastResponse!!.list!![4]!!.pressure.toString()
                        Config.clouds5 = weatherForecastResponse!!.list!![4]!!.clouds.toString()

                        editor.putString("day5",Config.day5)
                        editor.putString("temp5",Config.temp5)
                        editor.putString("pressure5",Config.pressure5)
                        editor.putString("humidity5",Config.humidity5)
                        editor.putString("clouds5",Config.clouds5)

                        editor.apply()

                    } else {
                        Log.i("error",response.code().toString())
                        Log.i("response",response.body().toString())
                        Log.i("response body",response.errorBody().toString())
                    }

                }

            })

        }

        fun setupSharedPreferences(){
            var retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WeatherBasicFragment.baseUrl)
                .build()

            var weatherService: WeatherService = retrofit.create(WeatherService::class.java)
            var call = weatherService.getCurrentWeatherData(
                WeatherBasicFragment.lat,
                WeatherBasicFragment.lon,
                WeatherBasicFragment.appId
            )
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                    if(response.code() == 200){
                        val weatherResponse: WeatherResponse? = response.body() as WeatherResponse

                        Config.city = weatherResponse!!.name
                        if(Config.units){
                            Config.temperature = getCelcius(weatherResponse!!.main!!.temp).toString() + " C"
                        } else {
                            Config.temperature = weatherResponse!!.main!!.temp.toString() + " K"
                        }
                        Config.pressure = weatherResponse!!.main!!.pressure.toString()
                        Config.weatherIcon = weatherResponse!!.weather!![0]!!.icon

                        val editor = sharedPreferences!!.edit()
                        editor.putString("city",Config.city)
                        editor.putString("temperature",Config.temperature)
                        editor.putString("pressure",Config.pressure)
                        editor.putString("weatherIcon",Config.weatherIcon)

                        Config.windSpeed = weatherResponse!!.wind!!.speed.toString()
                        Config.sky = weatherResponse!!.weather!![0].description
                        Config.humidity = weatherResponse!!.main!!.humidity.toString()
                        Config.clouds = weatherResponse!!.clouds!!.all.toString()

                        editor.putString("windSpeed",Config.windSpeed)
                        editor.putString("sky",Config.sky)
                        editor.putString("humidity",Config.humidity)
                        editor.putString("clouds",Config.clouds)
                        editor.apply()

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

}