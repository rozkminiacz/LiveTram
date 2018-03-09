package me.rozkmin.livetram.mapview.di

import dagger.Subcomponent
import me.rozkmin.livetram.mapview.MapActivity

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
@Subcomponent(modules = [(MapViewModule::class)])
interface MapViewComponent{
    fun inject(mapActivity: MapActivity)
}