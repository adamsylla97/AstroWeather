package com.example.astroweather

class CityData(){
    public var name: String = ""
    public var longitude: Double = 0.0
    public var latitude: Double = 0.0

    constructor(cName: String, cLongitude: Double, cLatitude: Double) : this() {
        name = cName
        longitude = cLongitude
        latitude = cLatitude
    }

    override fun toString(): String {
        return "name: $name longitude: $longitude latitude: $latitude"
    }
}