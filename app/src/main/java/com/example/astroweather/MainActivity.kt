package com.example.astroweather

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import org.joda.time.DateTime
import org.joda.time.Days
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private var fragmentColleactionAdapter: FragmentCollectionAdapter? = null
    private var tabletFragmentCollectionAdapter: TabletFragmentCollectionAdapter? = null

    var sharedPreferences: SharedPreferences? = null
    val PREFS_FILENAME = "myprefs"

    fun getTimeDifference(): Boolean{
        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME,0)
        Config.updateDate = sharedPreferences!!.getString("updateDate","null")

        var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        var date: Date = Date()

        var dateLastUpdate = Config.updateDate.substring(0,10) + " " + Config.updateDate.substring(12,20)
        var dateNow = dateFormat.format(date)

        var d1: Date? = null
        var d2: Date? = null

        var daysDiff = 0

        var dt1: DateTime = DateTime(Date())
        var dt2: DateTime

        try{
            d1 = dateFormat.parse(dateLastUpdate)
            d2 = dateFormat.parse(dateNow)

            dt1 = DateTime(d1)
            dt2 = DateTime(d2)
            val editor = sharedPreferences!!.edit()
            Config.updateDate = dt2.toString()
            editor.putString("updateDate",Config.updateDate)
            Log.i("datetimeabcde", "to jest pakowane: " + Config.updateDate)
            editor.apply()

            daysDiff = Days.daysBetween(dt1,dt2).days
            Log.i("datetimeabcde", "day1 " + dt1)
            Log.i("datetimeabcde", "day2 " + dt2)
            Log.i("datetimeabcde",daysDiff.toString())

        }catch (e: Exception){
            e.printStackTrace()
        }

        Log.i("datetimeabcde",daysDiff.toString() + " abb")

        when(daysDiff >= 1){
            true -> {
                return true
            }
            false -> return false
        }
    }

    fun createFavListCity(){

        var favCityList = ArrayList<CityData>()

        var city: CityData = CityData()
        city.name = "moje miasto"
        city.latitude = 42.14
        city.longitude = 31.01

        var city2: CityData = CityData()
        city2.name = "nie moje miasto"
        city2.longitude = 52.14
        city2.latitude = 21.01

        var city3: CityData = CityData()
        city3.name = "Kolobrzeg"
        city3.latitude = 54.10
        city3.longitude = 15.58

        favCityList.add(city)
        favCityList.add(city2)
        favCityList.add(city3)

        Config.favCities = favCityList
        //Utils.saveToSharedPreferences()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(PREFS_FILENAME,0)
        Utils.sharedPreferences = sharedPreferences
        Config.firstUsage = sharedPreferences!!.getBoolean("firstUsage",true)
        if(Config.firstUsage){
            createFavListCity()
            Toast.makeText(this,"FIRSTUSAGE",Toast.LENGTH_LONG).show()
            val editor = sharedPreferences!!.edit()
            editor.putString("updateDate",Config.updateDate)
            editor.putBoolean("firstUsage",false)
            editor.apply()
            Utils.saveToSharedPreferences()
        }

        var isTablet : Boolean = resources.getBoolean(R.bool.isTablet)
        if(!isTablet){
            viewPager = findViewById(R.id.pager)
            fragmentColleactionAdapter = FragmentCollectionAdapter(supportFragmentManager)
            viewPager.adapter = fragmentColleactionAdapter
            viewPager.offscreenPageLimit = 5
        } else {
            viewPager = findViewById(R.id.tabletPager)
            tabletFragmentCollectionAdapter = TabletFragmentCollectionAdapter(supportFragmentManager)
            viewPager.adapter = tabletFragmentCollectionAdapter
            viewPager.offscreenPageLimit = 5
        }

        var connected: Boolean = false
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(!isTablet){
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI
                ).state == NetworkInfo.State.CONNECTED
            ) {
                //we are connected to a network
                connected = true
                Config.isConnected = true
            } else{
                connected = false
                Config.isConnected = false
            }
        } else {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI
                ).state == NetworkInfo.State.CONNECTED
            ){
                //we are connected to a network
                connected = true
                Config.isConnected = true
            } else{
                connected = false
                Config.isConnected = false
            }
        }

        //shared preferences

        if(connected){
            Config.shouldUpdate = true
            if(getTimeDifference()){
                Utils.setupSharedPreferences()
                Utils.setupSharedPreferencesForForecastWeather()
                Toast.makeText(this,"we are connected and updated",Toast.LENGTH_LONG).show()
            } else {
                Config.shouldUpdate = false
                Utils.getDataFromSharedPreferences()
                Toast.makeText(this,"we are connected, but we were updatind recently",Toast.LENGTH_LONG).show()
            }
        } else {
            Config.shouldUpdate = false
            Utils.getDataFromSharedPreferences()
            Toast.makeText(this,"we are NOT connected, and data may be outdated",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == R.id.settings){
            openDialog()
        }
        else if (id == R.id.weatherSettings){
            openWeatherSettings()
        }
        else if (id == R.id.addCitySettings){
            openAddCityDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAddCityDialog() {
        var addCity: PopAddCity = PopAddCity()
        addCity.show(supportFragmentManager, "dialog add city")
    }

    private fun openWeatherSettings(){
        var popWeatherSettings:PopWeatherSettings = PopWeatherSettings()
        popWeatherSettings.show(supportFragmentManager, "dialog 2")
    }

    private fun openDialog() {
        var popSettings: PopSettings = PopSettings()
        popSettings.show(supportFragmentManager, "example dialog")
    }
}
