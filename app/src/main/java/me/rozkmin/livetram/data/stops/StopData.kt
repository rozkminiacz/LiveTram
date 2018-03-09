package me.rozkmin.livetram.data.stops

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
data class StopData(val category: String, val id: String, val latitude: Long, val longitude: Long, val name: String, val shortName: String) {
    fun getLatDouble() : Double = latitude.toDouble()/3600000.0
    fun getLonDouble() : Double = longitude.toDouble()/3600000.0
//    field private category:Ljava/lang/String;
//
//    .field private id:Ljava/lang/String;
//
//    .field private latitude:D
//
//    .field private longitude:D
//
//    .field private name:Ljava/lang/String;
//
//    .field private shortName:Ljava/lang/String;
}