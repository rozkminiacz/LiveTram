package me.rozkmin.livetram.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.realm.RealmConfiguration
import me.rozkmin.livetram.PresenterSchedulers
import me.rozkmin.livetram.data.di.DatabaseModule
import me.rozkmin.livetram.mapview.di.MapViewComponent
import me.rozkmin.livetram.mapview.di.MapViewModule
import me.rozkmin.livetram.network.NetworkModule
import javax.inject.Singleton

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
@Singleton
@Component(modules = [(NetworkModule::class), (DatabaseModule::class)])
interface AppComponent{

    fun plusMapViewComponent(mapViewModule: MapViewModule) : MapViewComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        @BindsInstance
        fun realmConfig(realmConfig: RealmConfiguration): Builder
        @BindsInstance
        fun schedulers(presenterSchedulers: PresenterSchedulers) : Builder
        fun build(): AppComponent
    }

}