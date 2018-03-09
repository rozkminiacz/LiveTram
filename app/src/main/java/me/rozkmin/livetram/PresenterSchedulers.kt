package me.rozkmin.livetram

import io.reactivex.Scheduler

/**
 * Created by jaroslawmichalik on 20.01.2018
 */
interface PresenterSchedulers {
    fun getBackgroundScheduler(): Scheduler
    fun getMainThreadScheduler(): Scheduler
}

