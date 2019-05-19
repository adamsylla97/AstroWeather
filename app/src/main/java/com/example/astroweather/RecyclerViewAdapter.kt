package com.example.astroweather

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var lista: Array<String> = arrayOf("jeden","dwa","trzy","cztery","piec")

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item,parent,false)
        var viewHolder: ViewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.i("onbindviewholder","called")

        when(p1){
            0 -> p0.name2.text = lista[0]
            1 -> p0.name2.text = lista[1]
            2 -> p0.name2.text = lista[2]
            3 -> p0.name2.text = lista[3]
            4 -> p0.name2.text = lista[4]
        }

        p0.name1.text = p1.toString()

    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var parentItemLayout: LinearLayout
        lateinit var name1: TextView
        lateinit var name2: TextView
        lateinit var name3: TextView

        init {
            parentItemLayout = itemView.findViewById(R.id.parentItemLayout)
            name1 = itemView.findViewById(R.id.name1)
            name2 = itemView.findViewById(R.id.name2)
            name3 = itemView.findViewById(R.id.name3)
        }

    }

}