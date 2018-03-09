package me.rozkmin.livetram.mapview.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.rozkmin.livetram.data.AbstractProvider
import me.rozkmin.livetram.data.stops.StopData
import me.rozkmin.livetram.data.stops.StopDataProvider
import me.rozkmin.livetram.data.tram.TramData
import me.rozkmin.livetram.data.tram.TramLocationDataProvider
import me.rozkmin.livetram.mapview.MapActivity
import me.rozkmin.livetram.mapview.MapContract
import me.rozkmin.livetram.mapview.MapManager
import me.rozkmin.livetram.mapview.MapPresenter
import me.rozkmin.livetram.mapview.StopMapManager
import me.rozkmin.livetram.mapview.TramPinManager

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
@Module
class MapViewModule(private val activity: MapActivity){
    @Provides
    fun provideMapPresenter(impl: MapPresenter) : MapContract.Presenter = impl

    @Provides
    fun provideStopsProvider(impl : StopDataProvider) : AbstractProvider<StopData> = impl

    @Provides
    fun provideTramProvider(impl : TramLocationDataProvider) : AbstractProvider<TramData> = impl

    @Provides
    fun provideStopPinManager(impl : StopMapManager) : MapManager<StopData> = impl

    @Provides
    fun provideTramPinManager(impl : TramPinManager) : MapManager<TramData> = impl

    @Provides
    fun provideContext() : Context = activity
}