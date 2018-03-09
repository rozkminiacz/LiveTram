package me.rozkmin.livetram.di

import android.app.Application
import android.content.Context
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import me.rozkmin.livetram.PresenterSchedulers

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
object AppModule {
    lateinit var appComponent: AppComponent

    fun attachApp(application: Application){
        createComponent(application)
    }

    private fun createComponent(app: Application) {
        val realmConfig = getRealmConfig(app)
        appComponent = DaggerAppComponent.builder()
                .realmConfig(realmConfig)
                .schedulers(getAndroidSchedulers())
                .application(app)
                .build()
    }

    private fun getRealmConfig(context: Context): RealmConfiguration {
        Realm.init(context)
        return RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
    }

    private fun getAndroidSchedulers() : PresenterSchedulers = object : PresenterSchedulers {
        override fun getBackgroundScheduler(): Scheduler {
            return Schedulers.io()
        }

        override fun getMainThreadScheduler(): Scheduler {
            return AndroidSchedulers.mainThread()
        }
    }

}