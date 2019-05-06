package com.example.astroweather


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import kotlinx.android.synthetic.main.fragment_moon.*
import kotlinx.android.synthetic.main.fragment_sun.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MoonFragment : Fragment() {

    lateinit var fragmentView: View

    fun initTextViews(){
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_moon, container, false)

        initTextViews()

        lateinit var astroCalculatorLocation: AstroCalculator.Location
        astroCalculatorLocation = AstroCalculator.Location(52.14,21.01)

        val astroDateTime = AstroDateTime(2019,5,3,20,43,11,1,true)
        astroDateTime.day = 3
        astroDateTime.month = 5
        astroDateTime.year = 2019

        val astroCalculator = AstroCalculator(astroDateTime,astroCalculatorLocation)
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.moonrise.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.moonset.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.nextNewMoon.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.nextFullMoon.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.illumination.toString())



//        activity!!.runOnUiThread { Runnable {
//            while (true){
//                Thread.sleep(1000)
//                actualTime.setText(currentDate)
//            }
//        } }

       // TimerSetup1(context!!).execute()

        return fragmentView
    }

    public fun update(text: String){
        activity!!.actualTimeMoon.setText(text)
    }


}
