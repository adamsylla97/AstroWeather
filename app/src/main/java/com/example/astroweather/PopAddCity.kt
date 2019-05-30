package com.example.astroweather

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import java.lang.Exception

class PopAddCity: AppCompatDialogFragment() {

    lateinit var nameCity: EditText
    lateinit var latitudeCity: EditText
    lateinit var longitudeCity: EditText

    fun addCityToList(newCity: CityData){
        var tempList = ArrayList<CityData>()
        tempList = Config.favCities as ArrayList<CityData>
        tempList.add(newCity)
        Config.favCities = tempList

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        var inflater: LayoutInflater = activity!!.layoutInflater
        var view: View = inflater.inflate(R.layout.activity_pop_add_city,null)

        builder.setView(view)
            .setNegativeButton("cancel",{ dialogInterface: DialogInterface, i: Int ->

            })
            .setPositiveButton("add",{ dialog: DialogInterface?, which: Int ->
                var name = nameCity.text.toString()
                var latitude = latitudeCity.text.toString().toDouble()
                var longitude = longitudeCity.text.toString().toDouble()
                var tempCity = CityData(name,longitude,latitude)
                addCityToList(tempCity)
                Toast.makeText(view.context,"city added",Toast.LENGTH_LONG).show()
            })

        nameCity = view.findViewById(R.id.nameCity)
        longitudeCity = view.findViewById(R.id.longitudeCity)
        latitudeCity = view.findViewById(R.id.latitudeCity)

        return builder.create()
    }

}