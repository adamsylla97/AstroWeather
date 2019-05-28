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
import java.lang.Exception

class PopAddCity: AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        var inflater: LayoutInflater = activity!!.layoutInflater
        var view: View = inflater.inflate(R.layout.activity_pop_add_city,null)
        builder.setView(view)
            .setNegativeButton("cancel",{ dialogInterface: DialogInterface, i: Int ->

            })
            .setPositiveButton("add",{ dialog: DialogInterface?, which: Int ->

            })

        return builder.create()
    }

}