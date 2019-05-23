package com.example.astroweather

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private var fragmentColleactionAdapter: FragmentCollectionAdapter? = null

    var wbf: WeatherBasicFragment = WeatherBasicFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var isTablet : Boolean = resources.getBoolean(R.bool.isTablet)
        if(!isTablet){
            viewPager = findViewById(R.id.pager)
            fragmentColleactionAdapter = FragmentCollectionAdapter(supportFragmentManager)
            viewPager.adapter = fragmentColleactionAdapter
        }

        var connected: Boolean = false

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            ).state == NetworkInfo.State.CONNECTED
        ) {
            //we are connected to a network
            connected = true
        } else
            connected = false

        if(connected){
            Toast.makeText(this,"we are connected",Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"we are NOT connected",Toast.LENGTH_LONG).show()
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
        else if (id == R.id.update){
            Config.shouldUpdate = true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDialog() {
        var popSettings: PopSettings = PopSettings()
        popSettings.show(supportFragmentManager, "example dialog")
    }
}
