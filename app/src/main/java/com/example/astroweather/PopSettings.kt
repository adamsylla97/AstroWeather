package com.example.astroweather

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Spinner

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


            })

        longitudeSettings = view.findViewById(R.id.longitudeSettings)
        latitudeSettings = view.findViewById(R.id.latitudeSettings)
        settingsSpinner = view.findViewById(R.id.settingsSpinner)

        return builder.create()
    }
}
