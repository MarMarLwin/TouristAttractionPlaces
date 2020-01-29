package com.mml.travelplanning.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AttractionPlaces (
    @SerializedName("id")
    var id:Int?=0,
    @SerializedName("place_name")
    var place_name:String?="",
    @SerializedName("address")
    var address:String?="",
    @SerializedName("ticketPrice")
    var ticketPrice:Double=0.0,
    @SerializedName("currency")
    var currency:String="",
    @SerializedName("open_hr")
    var open_hr:String,
    @SerializedName("url")
    var url:String="",
    @SerializedName("geolocation")
    var geolocation:GeoLocation
):Serializable