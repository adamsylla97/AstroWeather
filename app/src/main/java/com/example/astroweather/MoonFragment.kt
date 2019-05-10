package com.example.astroweather

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import kotlinx.android.synthetic.main.fragment_moon.*
import org.joda.time.DateTime
import org.joda.time.Days
import java.lang.Exception
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

    lateinit var astroCalculatorLocation: AstroCalculator.Location
    lateinit var astroCalculator: AstroCalculator
    var latitudeData: Double = Config.latitude
    var longitudeData: Double = Config.longitude
    val astroDateTime = AstroDateTime()
    lateinit var inflater: LayoutInflater
    var container: ViewGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_moon, container, false)
        retainInstance = true

        initTextViews()

        astroDateTime.timezoneOffset = 1
        astroDateTime.isDaylightSaving = true

        update()

        Log.i("AstroCalc MOON",astroCalculator.moonInfo.moonrise.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.moonset.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.nextNewMoon.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.nextFullMoon.toString())
        Log.i("AstroCalc MOON",astroCalculator.moonInfo.illumination.toString())

        //clock part
        val sdf = SimpleDateFormat("HH:mm:ss")

        Thread(Runnable {
            var iterator: Int = 0
            while (true){
                var currentDate = sdf.format(Date())
                currentDate = sdf.format(Date())

                try{
                    if(activity != null){
                        activity!!.runOnUiThread {
                            actualTimeMoon.text = currentDate.toString()
                            if(Config.sendToast && fragmentView != null){
                                Toast.makeText(fragmentView.context,"Dozwolone dane: latitude -90 90 longitude -180 180",Toast.LENGTH_LONG).show()
                            }
                            if((longitudeData!= Config.longitude || latitudeData!= Config.latitude) && Config.invalidData == false){
                                latitudeData = Config.latitude
                                longitudeData = Config.longitude
                                update()
                            }
                            if(iterator > Config.refreshRate){
                                update()
                                Toast.makeText(view!!.context,"refreshed",Toast.LENGTH_LONG).show()
                                iterator = 0
                            }
                        }
                    }
                } catch (e: Exception){
                    if(activity != null){
                        activity!!.finish()
                    }
                }
                Thread.sleep(1000)
                iterator++
            }
        }).start()


        return fragmentView
    }

    private fun update(){
        try{
            val sdf: SimpleDateFormat = SimpleDateFormat("dd.M.yyyy HH:mm:ss")
            val currentDate = sdf.format(Date())
            Log.i("date time moon",currentDate)
            var tempList: List<String> = currentDate.split(" ", ".",":")
            astroDateTime.day = tempList[0].toInt()
            astroDateTime.month = tempList[1].toInt()
            astroDateTime.year = tempList[2].toInt()
            astroDateTime.hour = tempList[3].toInt()
            astroDateTime.minute = tempList[4].toInt()
            astroDateTime.second = tempList[5].toInt()
            astroCalculatorLocation = AstroCalculator.Location(latitudeData,longitudeData)
            astroCalculator = AstroCalculator(astroDateTime,astroCalculatorLocation)

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
            synodicDay.text = getSynodicDay()

            illumination.text = tempIllumination.toString().substring(0,10) + "%"

            getSynodicDay()

            Config.invalidData = false
        }catch (e: Exception){
            Toast.makeText(context,"Niedozwolone dane",Toast.LENGTH_LONG).show()
            Config.invalidData = true
        }
    }

    private fun getSynodicDay(): String{

        var difference: String
        var newMoon: String = astroCalculator.moonInfo.nextNewMoon.toString()

        val sdf: SimpleDateFormat = SimpleDateFormat("dd.M.yyyy HH:mm:ss")
        val currentDate = sdf.format(Date())

        Log.i("SYNODIC DAY","INFO")
        Log.i("actualTime moon", newMoon)
        Log.i("actualTime", currentDate)

        var tempList: List<String>
        tempList = newMoon.split(" ",".")
        Log.i("tempList",tempList[0]+ " " + tempList[1] + " " + tempList[2])
        var start: DateTime = DateTime(tempList[2].toInt(),tempList[1].toInt(),tempList[0].toInt(),0,0,0,0)
        tempList = currentDate.split(" ",".")
        var end: DateTime = DateTime(tempList[2].toInt(),tempList[1].toInt(),tempList[0].toInt(),0,0,0,0)
        difference = Days.daysBetween(start,end).toString()


        var synodicDay = difference.substring(1,difference.length-1)
        Log.i("actualTime between",synodicDay)

        if(synodicDay.toDouble() < 0){
            synodicDay = (synodicDay.toDouble() + 29.531).toString()
            synodicDay = synodicDay.split(".")[0]
        }

        return synodicDay
    }
}
