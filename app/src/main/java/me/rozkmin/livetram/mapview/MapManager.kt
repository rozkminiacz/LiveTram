package me.rozkmin.livetram.mapview

import com.google.android.gms.maps.GoogleMap

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
interface MapManager<Pin>{
    fun attachAndConfigure(googleMap: GoogleMap)
    fun update(list : List<Pin>)
    fun removeAll()
    fun onMarkerClick(function : (Pin) -> Unit)
    fun filter(filter: Filter)

    sealed class Filter{
        object All : Filter()
        object None : Filter()
        data class Query(val filterFunction: (Any?) -> Boolean) : Filter()
    }
}
