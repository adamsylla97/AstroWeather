package com.example.astroweather

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import java.lang.Exception

class PopSettings : AppCompatDialogFragment() {

    lateinit var longitudeSettings: EditText
    lateinit var latitudeSettings: EditText
    lateinit var settingsSpinner: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        var inflater: LayoutInflater = activity!!.layoutInflater
        var view: View = inflater.inflate(R.layout.activity_pop_settings,null)
        builder.setView(view)
            .setNegativeButton("cancel",{dialogInterface: DialogInterface, i: Int ->

            })
            .setPositiveButton("ok",{dialog: DialogInterface?, which: Int ->
                var longitude: String = longitudeSettings.text.toString()
                var latitude: String = latitudeSettings.text.toString()
                var validatedData: List<Double> = validateData(longitude,latitude)
                Log.i("new longitude",validatedData[0].toString())
                Log.i("new latitude",validatedData[1].toString())
                Config.longitude = validatedData[0]
                Config.latitude = validatedData[1]

                Log.i("config longi",Config.longitude.toString())
                Log.i("config latit",Config.latitude.toString())

            })

        longitudeSettings = view.findViewById(R.id.longitudeSettings)
        latitudeSettings = view.findViewById(R.id.latitudeSettings)
        settingsSpinner = view.findViewById(R.id.settingsSpinner)

        return builder.create()
    }

    fun validateData(longitude: String, latitude: String): List<Double>{
        var validatedData: MutableList<Double> = mutableListOf(0.0,0.0)
        try{
            validatedData.set(0,longitude.toDouble())
            validatedData.set(1,latitude.toDouble())
        }catch (e: Exception){
            Log.i("ERROR POPSETTINGS","niepoprawne dane")
            validatedData.set(0,Config.longitude)
            validatedData.set(1,Config.latitude)
        }
        return validatedData
    }

}
