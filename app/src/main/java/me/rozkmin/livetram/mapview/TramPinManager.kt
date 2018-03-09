package me.rozkmin.livetram.mapview

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import me.rozkmin.livetram.R
import me.rozkmin.livetram.data.tram.TramData
import javax.inject.Inject

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class TramPinManager @Inject constructor(val context: Context) : MapManager<TramData> {

    lateinit var googleMap: GoogleMap

    var items: MutableMap<String, Marker> = mutableMapOf()

    private var list: List<TramData> = listOf()

    companion object {
        val TAG: String = TramPinManager::class.java.simpleName
    }

    override fun attachAndConfigure(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    override fun update(list: List<TramData>) {
        Log.d(StopMapManager.TAG, "update: ${list.size}")
        list.forEach {
            when {
                items.containsKey(it.id) -> items[it.id]!!.update(it)
                else -> items[it.id] = googleMap.addMarker(it.createMarkerOptions())
            }
        }
        this.list=list
        Log.d(StopMapManager.TAG, "update: items: ${items.size}")
    }

    override fun removeAll() {
        items.values.forEach { it.remove() }
    }

    private fun TramData.createMarkerOptions(): MarkerOptions {
        return MarkerOptions().apply {
            title(name)
            icon(BitmapDescriptorFactory.fromBitmap(context.getBitmap(R.drawable.tram_blue_small)))
            rotation(angle)
            position(LatLng(lat, lon))
        }
    }

    private fun Marker.update(it: TramData) {
        position = LatLng(it.lat, it.lon)
        rotation = it.angle

    }

    override fun onMarkerClick(function: (TramData) -> Unit) {

    }

    override fun filter(filter: MapManager.Filter) {
        when (filter) {
            MapManager.Filter.All -> items.forEach {
                it.value.isVisible = true
                it.value.setIcon(iconFor(false))
            }
            MapManager.Filter.None -> items.forEach { it.value.isVisible = false }
            is MapManager.Filter.Query -> items.forEach {

                filter.filterFunction(it.key.let { tram(it) }).apply {
                    it.value.isVisible = this
                    it.value.setIcon(iconFor(this))
                }
            }
        }
    }

    private fun tram(id: String): TramData? {
        return list.first { it.id.contentEquals(id) }
    }

    private fun iconFor(selected: Boolean): BitmapDescriptor? {
        val icon = if (selected) R.drawable.tram_magenta_middle else R.drawable.tram_blue_small

        return BitmapDescriptorFactory.fromBitmap(context.getBitmap(icon))
    }

    private fun Context.getBitmap(resourceId: Int) =
            (ContextCompat.getDrawable(this, resourceId) as BitmapDrawable).bitmap

}