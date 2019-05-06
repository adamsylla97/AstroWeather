package com.example.astroweather

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
import java.text.SimpleDateFormat
import java.util.*


class MoonFragment : Fragment() {

    lateinit var fragmentView: View
    lateinit var longitudeMoon: TextView
    lateinit var latitudeMoon: TextView
    lateinit var moonRise: TextView
    lateinit var moonSet: TextView
    lateinit var nextNewMoon: TextView
    lateinit var nextFullMoon: TextView
    lateinit var illumination: TextView
    lateinit var synodicDay: TextView

    fun initTextViews(){
        longitudeMoon = fragmentView.findViewById(R.id.longitudeMoon)
        latitudeMoon = fragmentView.findViewById(R.id.latitudeMoon)
        moonRise = fragmentView.findViewById(R.id.moonRise)
        moonSet = fragmentView.findViewById(R.id.moonSet)
        nextNewMoon = fragmentView.findViewById(R.id.nextNewMoon)
        nextFullMoon = fragmentView.findViewById(R.id.nextFullMoon)
        illumination = fragmentView.findViewById(R.id.illumination)
        synodicDay = fragmentView.findViewById(R.id.synodicDay)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_moon, container, false)

        initTextViews()

        var latitudeData: Double = 52.14
        var longitudeData: Double = 21.01

        lateinit var astroCalculatorLocation: AstroCalculator.Location
        astroCalculatorLocation = AstroCalculator.Location(latitudeData,longitudeData)

       // val astroDateTime = AstroDateTime(2019,5,6,20,43,11,0,true)
        val astroDateTime = AstroDateTime()
        astroDateTime.day = 24
        astroDateTime.month = 4
        astroDateTime.year = 2019
        astroDateTime.hour = 20
        astroDateTime.minute = 26
        astroDateTime.second = 42
        astroDateTime.timezoneOffset = 1
        astroDateTime.isDaylightSaving = true

        val astroCalculator = AstroCalculator(astroDateTime,astroCalculatorLocation)
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.moonrise.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.moonset.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.nextNewMoon.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.nextFullMoon.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.illumination.toString())

        longitudeMoon.text = longitudeData.toString()
        latitudeMoon.text = latitudeData.toString()

        var temp: List<String>? = null
        temp = astroCalculator.moonInfo.moonrise.toString().split(" ")
        moonRise.text = temp[1]
        temp = astroCalculator.moonInfo.moonset.toString().split(" ")
        moonSet.text = temp[1]
        temp = astroCalculator.moonInfo.nextNewMoon.toString().split(" ")
        nextNewMoon.text = temp[0] + " " + temp[1]
        temp = astroCalculator.moonInfo.nextFullMoon.toString().split(" ")
        nextFullMoon.text = temp[0] + " " + temp[1]
        var tempIllumination: Double = astroCalculator.moonInfo.illumination
        tempIllumination = tempIllumination*100
        synodicDay.text = astroCalculator.moonInfo.age.toString()

        illumination.text = tempIllumination.toString().substring(0,4) + "%"

        //clock part
        val sdf = SimpleDateFormat("HH:mm:ss")

        Thread(Runnable {
            while (true){
                var currentDate = sdf.format(Date())
                currentDate = sdf.format(Date())

                activity!!.runOnUiThread {
                    actualTimeMoon.text = currentDate.toString()
                }
                Thread.sleep(1000)
            }
        }).start()


        return fragmentView
    }

    public fun update(text: String){
        activity!!.actualTimeMoon.text = text
    }
}
