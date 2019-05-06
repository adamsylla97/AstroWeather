package com.example.astroweather

import android.content.Context
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

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private var fragmentColleactionAdapter: FragmentCollectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isTablet : Boolean = resources.getBoolean(R.bool.isTablet)
        if(!isTablet){
            viewPager = findViewById(R.id.pager)
            fragmentColleactionAdapter = FragmentCollectionAdapter(supportFragmentManager)
            viewPager.adapter = fragmentColleactionAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == R.id.settings){
            Log.i("Settings","settings button clicked")
        }
        return super.onOptionsItemSelected(item)
    }

//    private inner class TimerSetup(c: Context) : AsyncTask<Void, Void, String>(){
//        override fun doInBackground(vararg params: Void?): String? {
//
//            val sdf = SimpleDateFormat("HH:mm:ss")
//            var currentDate = sdf.format(Date())
//
//            while(true){
//                Log.i("time sun",currentDate)
//
//                Thread.sleep(1000)
//                currentDate = sdf.format(Date())
//                //actualTime.setText(currentDate)
//                activity!!.runOnUiThread {
//                    actualTime.setText(currentDate)
//                }
//            }
//
//            return null
//        }
//
//    }
}
