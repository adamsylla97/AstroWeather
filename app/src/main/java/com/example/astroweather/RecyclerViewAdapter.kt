package com.example.astroweather

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class RecyclerViewAdapter(weatherForecast: ArrayList<ForecastDayInformation>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var lista: List<ForecastDayInformation> = weatherForecast

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item,parent,false)
        var viewHolder: ViewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        Log.i("listasize",lista.size.toString())
        return lista.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.i("onbindviewholder","called")

        Log.i("inrecycle",lista[p1].toString())

        p0.day.text = p1.toString()
        p0.temp.text = lista[p1].temp
        p0.humidity.text = lista[p1].humidity
        p0.pressure.text = lista[p1].pressure
        p0.clouds.text = lista[p1].clouds

        Log.i("recycledata123",p1.toString())
        Log.i("recycledata123",lista[p1].toString())

    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var parentItemLayout: LinearLayout
        var day: TextView
        var temp: TextView
        var humidity: TextView
        var pressure: TextView
        var clouds: TextView

        init {
            parentItemLayout = itemView.findViewById(R.id.parentItemLayout)
            day = itemView.findViewById(R.id.day)
            temp = itemView.findViewById(R.id.temp)
            humidity = itemView.findViewById(R.id.humidity)
            pressure = itemView.findViewById(R.id.pressure)
            clouds = itemView.findViewById(R.id.clouds)
        }

    }

}