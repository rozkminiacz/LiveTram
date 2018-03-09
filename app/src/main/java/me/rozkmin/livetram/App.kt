package me.rozkmin.livetram

import android.app.Application
import me.rozkmin.livetram.di.AppModule

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppModule.attachApp(this)
    }
}