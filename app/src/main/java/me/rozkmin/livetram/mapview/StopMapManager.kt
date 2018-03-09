package me.rozkmin.livetram.mapview

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import me.rozkmin.livetram.R
import me.rozkmin.livetram.data.stops.StopData
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class StopMapManager @Inject constructor(val context: Context) : MapManager<StopData> {

    companion object {
        val TAG: String = StopMapManager::class.java.simpleName
    }

    lateinit var googleMap: GoogleMap

    var items: MutableMap<StopData, Marker> = mutableMapOf()

    override fun attachAndConfigure(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val krk = LatLng(50.052, 19.944)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(krk, 13f))


    }

    override fun onMarkerClick(function: (StopData) -> Unit) {
        googleMap.setOnMarkerClickListener { marker ->
            items.keys.forEach {
                if (items[it] != null && items[it] == marker) {
                    function(it)
                }
            }
            true
        }
    }

    override fun update(list: List<StopData>) {
        Log.d(TAG, "update: ${list.size}")
        list.forEach {
            when {
                items.containsKey(it) -> items[it]!!.update(it)
                else -> items[it] = googleMap.addMarker(it.createMarkerOptions())
            }
        }
        Log.d(TAG, "update: items: ${items.size}")
    }

    override fun removeAll() {
        items.values.forEach { it.remove() }
    }

    override fun filter(filter: MapManager.Filter) {
        when (filter) {
            MapManager.Filter.All -> items.forEach {
                it.value.isVisible = true
                it.value.setIcon(iconFor(false))
            }
            MapManager.Filter.None -> items.forEach { it.value.isVisible = false }
            is MapManager.Filter.Query -> items.forEach {
                filter.filterFunction(it.key).apply {
                    it.value.isVisible = this
                    it.value.setIcon(iconFor(this))
                }
            }
        }
    }

    private fun iconFor(selected: Boolean): BitmapDescriptor? {
        val icon = if (selected) R.drawable.station_green else R.drawable.station_green_small

        return BitmapDescriptorFactory.fromBitmap(context.getBitmap(icon))
    }


    private fun StopData.createMarkerOptions(): MarkerOptions {
        return MarkerOptions()
                .title(name)
                .icon(iconFor(false))
                .position(LatLng(getLatDouble(), getLonDouble()))
    }

    private fun Marker.update(it: StopData) {
        position = LatLng(it.getLatDouble(), it.getLonDouble())
    }

    private fun Context.getBitmap(resourceId: Int) =
            (ContextCompat.getDrawable(this, resourceId) as BitmapDrawable).bitmap

}
