package me.rozkmin.livetram.mapview

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import com.google.android.gms.maps.SupportMapFragment
import me.rozkmin.livetram.R
import me.rozkmin.livetram.data.stops.StopData
import me.rozkmin.livetram.data.tram.TramData
import me.rozkmin.livetram.databinding.ActivityMapBinding
import me.rozkmin.livetram.di.AppModule
import me.rozkmin.livetram.mapview.di.MapViewModule
import javax.inject.Inject

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class MapActivity : AppCompatActivity(), MapContract.MapView {

    companion object {
        val TAG: String = MapActivity::class.java.simpleName
    }

    @Inject
    lateinit var presenter: MapContract.Presenter

    @Inject
    lateinit var stopsPinMapManager: MapManager<StopData>

    @Inject
    lateinit var tramPinManager: MapManager<TramData>

    lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        loadMap {
            presenter.attach(this)
        }

        initSearch()
    }

    fun inject() = AppModule.appComponent
            .plusMapViewComponent(MapViewModule(this))
            .inject(this)

    private fun initSearch() {
        binding.activityMapSearchStop.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    when {
                        query.isEmpty() -> stopsPinMapManager.filter(MapManager.Filter.All)
                        query.trim().isEmpty() -> stopsPinMapManager.filter(MapManager.Filter.All)
                        else -> filterStops(query.toString())
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.run {
                    when {
                        newText.isEmpty() -> stopsPinMapManager.filter(MapManager.Filter.All)
                        newText.trim().isEmpty() -> stopsPinMapManager.filter(MapManager.Filter.All)
                    }
                }
                return true
            }

        })

        binding.activityMapSearchTram.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    when {
                        query.isEmpty() -> tramPinManager.filter(MapManager.Filter.All)
                        query.trim().isEmpty() -> tramPinManager.filter(MapManager.Filter.All)
                        else -> filterTrams(query.toString())
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.run {
                    when {
                        newText.isEmpty() -> tramPinManager.filter(MapManager.Filter.All)
                        newText.trim().isEmpty() -> tramPinManager.filter(MapManager.Filter.All)
                    }
                }
                return true
            }
        })
    }

    private fun loadMap(block: () -> Unit) {
        val mapFragment = SupportMapFragment.newInstance()

        supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.map_container, mapFragment)
                ?.commit()

        mapFragment.getMapAsync {
            stopsPinMapManager.attachAndConfigure(it)
            tramPinManager.attachAndConfigure(it)

            block.invoke()
        }
    }

    private fun filterStops(query: String) {
        stopsPinMapManager.filter(MapManager.Filter.Query {
            if (it is StopData) {
                it.name.toLowerCase().contains(query.toLowerCase())
            } else false
        })
    }

    private fun filterTrams(query: String) {
        tramPinManager.filter(MapManager.Filter.Query {
            if (it is TramData) {
                it.name.toLowerCase().contains(query.toLowerCase())
            } else false
        })
    }


    override fun updateTrams(list: List<TramData>) {
        tramPinManager.update(list)

    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun displayError(throwable: Throwable) {
        Log.e(TAG, "displayError: ", throwable)
    }

    override fun updateStops(list: List<StopData>) {
        stopsPinMapManager.update(list)
    }

}