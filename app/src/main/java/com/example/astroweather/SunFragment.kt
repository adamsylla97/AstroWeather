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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SunFragment : Fragment() {

    lateinit var fragmentView: View
    lateinit var sunRise: TextView
    lateinit var sunSet: TextView
    lateinit var sunCivRise: TextView
    lateinit var sunCivSet: TextView

    fun initTextViews(){
        sunRise = fragmentView.findViewById(R.id.sunRise)
        sunSet = fragmentView.findViewById(R.id.sunSet)
        sunCivRise = fragmentView.findViewById(R.id.sunCivRise)
        sunCivSet = fragmentView.findViewById(R.id.sunCivSet)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_sun, container, false)

        initTextViews()

        lateinit var astroCalculatorLocation: AstroCalculator.Location
        astroCalculatorLocation = AstroCalculator.Location(52.14,21.01)

        val astroDateTime = AstroDateTime()
        astroDateTime.day = 24
        astroDateTime.month = 12
        astroDateTime.year = 2019

        val astroCalculator = AstroCalculator(astroDateTime,astroCalculatorLocation)
        Log.i("AstroCalc SUN RISE",astroCalculator.sunInfo.sunrise.toString())
        Log.i("AstroCalc SUN SET",astroCalculator.sunInfo.sunset.toString())
        Log.i("AstroCalc CIV SUN RISE",astroCalculator.sunInfo.twilightMorning.toString())
        Log.i("AstroCalc CIV SUN SET",astroCalculator.sunInfo.twilightEvening.toString())

        var temp: List<String>? = null
        temp = astroCalculator.sunInfo.sunrise.toString().split(" ")
        sunRise.text = temp[1]
        temp = astroCalculator.sunInfo.sunset.toString().split(" ")
        sunSet.text = temp[1]
        temp = astroCalculator.sunInfo.twilightMorning.toString().split(" ")
        sunCivRise.text = temp[1]
        temp = astroCalculator.sunInfo.twilightEvening.toString().split(" ")
        sunCivSet.text = temp[1]

        return fragmentView
    }


}
