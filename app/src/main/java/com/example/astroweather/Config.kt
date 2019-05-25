package com.example.astroweather

object Config{
    //astro part
    public var longitude: Double = 21.01
    public var latitude: Double = 52.14
    public var invalidData: Boolean = false
    public var refreshRate: Int = 900
    public var sendToast: Boolean = false

    //weather part
    public var longitudeWeather: Double = 21.01
    public var latitudeWeahter: Double = 52.14
    public var weatherInvalidData: Boolean = false

    public var shouldUpdate: Boolean = false
    public var firstUsage: Boolean = true

    public var userUpdate: Boolean = false
    public var units: Boolean = true //true = celcius false = kelvin

    //shared preferences
    //WeatherBasicFragment
    public var city: String? = null
    public var temperature: String? = null
    public var pressure: String? = null
    public var weatherIcon: String? = null

    //WeatherAdditionalFragment
    public var windSpeed: String? = null
    public var humidity: String? = null
    public var sky: String? = null
    public var clouds: String? = null

    //WeatherForecastFragment
    //1
    public var day1: String? = null
    public var temp1: String? = null
    public var pressure1: String? = null
    public var humidity1: String? = null
    public var clouds1: String? = null

    //2
    public var day2: String? = null
    public var temp2: String? = null
    public var pressure2: String? = null
    public var humidity2: String? = null
    public var clouds2: String? = null

    //3
    public var day3: String? = null
    public var temp3: String? = null
    public var pressure3: String? = null
    public var humidity3: String? = null
    public var clouds3: String? = null

    //4
    public var day4: String? = null
    public var temp4: String? = null
    public var pressure4: String? = null
    public var humidity4: String? = null
    public var clouds4: String? = null

    //5
    public var day5: String? = null
    public var temp5: String? = null
    public var pressure5: String? = null
    public var humidity5: String? = null
    public var clouds5: String? = null
}