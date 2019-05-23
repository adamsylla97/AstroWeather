package com.example.astroweather

class ForecastDayInformation(t:String, h:String, p:String, c:String) {

    var temp: String
    var humidity: String
    var pressure: String
    var clouds: String

    init {
        temp = t
        humidity = h
        pressure = p
        clouds = c
    }

    override fun toString(): String {
        return "temp: $temp humidity: $humidity pressure: $pressure clouds: $clouds"
    }
}