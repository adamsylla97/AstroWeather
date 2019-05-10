package com.example.astroweather


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
import kotlinx.android.synthetic.main.fragment_sun.*
import org.joda.time.DateTime
import java.lang.Exception
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class SunFragment : Fragment() {

    lateinit var fragmentView: View
    lateinit var sunRise: TextView
    lateinit var sunSet: TextView
    lateinit var sunCivRise: TextView
    lateinit var sunCivSet: TextView
    lateinit var longitudeSun: TextView
    lateinit var latitudeSun: TextView


    fun initTextViews(){
        sunRise = fragmentView.findViewById(R.id.sunRise)
        sunSet = fragmentView.findViewById(R.id.sunSet)
        sunCivRise = fragmentView.findViewById(R.id.sunCivRise)
        sunCivSet = fragmentView.findViewById(R.id.sunCivSet)
        longitudeSun = fragmentView.findViewById(R.id.longitudeSun)
        latitudeSun = fragmentView.findViewById(R.id.latitudeSun)
    }

    lateinit var astroCalculator: AstroCalculator
    lateinit var astroCalculatorLocation: AstroCalculator.Location
    var latitudeData: Double = Config.latitude
    var longitudeData: Double = Config.longitude
    val astroDateTime = AstroDateTime()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_sun, container, false)
        retainInstance = true

        initTextViews()

        astroCalculatorLocation = AstroCalculator.Location(latitudeData,longitudeData)

        astroDateTime.timezoneOffset = 1
        astroDateTime.isDaylightSaving = true

        update()

        Log.i("AstroCalc SUN RISE",astroCalculator.sunInfo.sunrise.toString())
        Log.i("AstroCalc SUN SET",astroCalculator.sunInfo.sunset.toString())
        Log.i("AstroCalc CIV SUN RISE",astroCalculator.sunInfo.twilightMorning.toString())
        Log.i("AstroCalc CIV SUN SET",astroCalculator.sunInfo.twilightEvening.toString())

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
                            if(Config.sendToast && fragmentView != null){
                                Toast.makeText(fragmentView.context,"Dozwolone dane: latitude -90 90 longitude -180 180",Toast.LENGTH_LONG).show()
                                Config.sendToast = false
                            }
                            if((longitudeData!= Config.longitude || latitudeData!= Config.latitude) && Config.invalidData == false ){
                                latitudeData = Config.latitude
                                longitudeData = Config.longitude
                                update()
                            }
                            if(iterator > Config.refreshRate){
                                update()
                                Toast.makeText(view!!.context,"refreshed",Toast.LENGTH_LONG).show()
                                iterator = 0
                            }
                            actualTimeSun.text = currentDate.toString()
                        }
                    }
                } catch (e: Exception){
                    if(activity != null){
                        activity!!.finish()
                    }
                }

               Thread.sleep(1000)
               iterator ++

            }
        }).start()

        return fragmentView
    }

    public fun update(){
        val sdf: SimpleDateFormat = SimpleDateFormat("dd.M.yyyy HH:mm:ss")
        val currentDate = sdf.format(Date())
        Log.i("date time sun",currentDate)
        var tempList: List<String> = currentDate.split(" ", ".",":")
        astroDateTime.day = tempList[0].toInt()
        astroDateTime.month = tempList[1].toInt()
        astroDateTime.year = tempList[2].toInt()
        astroDateTime.hour = tempList[3].toInt()
        astroDateTime.minute = tempList[4].toInt()
        astroDateTime.second = tempList[5].toInt()
        astroCalculatorLocation = AstroCalculator.Location(latitudeData,longitudeData)
        astroCalculator = AstroCalculator(astroDateTime,astroCalculatorLocation)
        longitudeSun.text = longitudeData.toString()
        latitudeSun.text = latitudeData.toString()
        var temp: List<String>? = null
        temp = astroCalculator.sunInfo.sunrise.toString().split(" ")
        sunRise.text = temp[1]
        temp = astroCalculator.sunInfo.sunset.toString().split(" ")
        sunSet.text = temp[1]
        temp = astroCalculator.sunInfo.twilightMorning.toString().split(" ")
        sunCivRise.text = temp[1]
        temp = astroCalculator.sunInfo.twilightEvening.toString().split(" ")
        sunCivSet.text = temp[1]
    }
}
