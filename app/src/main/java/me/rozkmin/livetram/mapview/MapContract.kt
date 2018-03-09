package me.rozkmin.livetram.mapview

import me.rozkmin.livetram.BasePresenter
import me.rozkmin.livetram.BaseView
import me.rozkmin.livetram.data.stops.StopData
import me.rozkmin.livetram.data.tram.TramData

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
interface MapContract{
    interface MapView : BaseView{
        fun updateTrams(list : List<TramData>)
        fun updateStops(list : List<StopData>)
    }
    interface Presenter : BasePresenter<MapView>{

    }
}