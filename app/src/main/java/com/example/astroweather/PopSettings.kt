package com.example.astroweather

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_pop_settings.*
import java.lang.Exception

class PopSettings : AppCompatDialogFragment() {

    lateinit var longitudeSettings: EditText
    lateinit var latitudeSettings: EditText
    lateinit var settingsSpinner: Spinner
    var listOfItems = arrayOf("15 sekund","30 minut", "1 godzina", "3 godziny", "6 godzin")
    var refreshRate: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        var inflater: LayoutInflater = activity!!.layoutInflater
        var view: View = inflater.inflate(R.layout.activity_pop_settings,null)
        builder.setView(view)
            .setNegativeButton("cancel",{dialogInterface: DialogInterface, i: Int ->

            })
            .setPositiveButton("UPDATE",{dialog: DialogInterface?, which: Int ->
                var longitude: String = longitudeSettings.text.toString()
                var latitude: String = latitudeSettings.text.toString()

                var validatedData: List<Double> = validateData(longitude,latitude)
                Log.i("new longitude",validatedData[0].toString())
                Log.i("new latitude",validatedData[1].toString())
                    Config.longitude = validatedData[0]
                    Config.latitude = validatedData[1]

                Config.refreshRate = refreshRate
                Log.i("config refresh",Config.refreshRate.toString())

            })

        longitudeSettings = view.findViewById(R.id.longitudeSettings)
        latitudeSettings = view.findViewById(R.id.latitudeSettings)

        settingsSpinner = view.findViewById(R.id.settingsSpinner)

        settingsSpinner.adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item, listOfItems)
        settingsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(view != null){
                    Toast.makeText(view!!.context,listOfItems[position], Toast.LENGTH_LONG).show()
                }
                when(position){
                    0 -> refreshRate = 15
                    1 -> refreshRate = 1800
                    2 -> refreshRate = 3600
                    3 -> refreshRate = 10800
                    4 -> refreshRate = 21600
                }
            }

        }

        return builder.create()
    }

    fun validateData(longitude: String, latitude: String): List<Double>{
        var validatedData: MutableList<Double> = mutableListOf(0.0,0.0)
        try{
            validatedData.set(0, longitude.toDouble())
            validatedData.set(1, latitude.toDouble())
            if(((validatedData[0] < -180.0 || validatedData[0] > 180.0) && (validatedData[1] < -90.0 || validatedData[1] > 90.0))) {


                Log.i("ERROR POPSETTINGS","niepoprawne dane")
                validatedData.set(0,Config.longitude)
                validatedData.set(1,Config.latitude)
                Config.sendToast = true
            }
        }catch (e: Exception){
            Log.i("ERROR POPSETTINGS","niepoprawne dane")
            validatedData.set(0,Config.longitude)
            validatedData.set(1,Config.latitude)
        }
        return validatedData
    }

}
