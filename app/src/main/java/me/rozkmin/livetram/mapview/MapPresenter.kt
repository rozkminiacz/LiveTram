package me.rozkmin.livetram.mapview

import io.reactivex.Flowable
import me.rozkmin.livetram.PresenterSchedulers
import me.rozkmin.livetram.data.AbstractProvider
import me.rozkmin.livetram.data.stops.StopData
import me.rozkmin.livetram.data.tram.TramData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class MapPresenter @Inject constructor(
        private val tramProvider: AbstractProvider<TramData>,
        private val stopsProvider : AbstractProvider<StopData>,
        private val schedulers: PresenterSchedulers) : MapContract.Presenter {

    private var view: MapContract.MapView? = null

    override fun attach(view: MapContract.MapView) {
        this.view = view
        loadStops()
        loadTrams()
    }

    private fun loadStops(){
        stopsProvider.getAll()
                .subscribeOn(schedulers.getBackgroundScheduler())
                .observeOn(schedulers.getMainThreadScheduler())
                .doOnSuccess { view?.updateStops(it) }
                .doOnError { view?.displayError() }
                .subscribe()
    }

    private fun loadTrams(){
        Flowable.interval(0, 1500, TimeUnit.MILLISECONDS)
                .flatMap { tramProvider.getAll().toFlowable() }
                .subscribeOn(schedulers.getBackgroundScheduler())
                .observeOn(schedulers.getMainThreadScheduler())
                .doOnNext { view?.updateTrams(it) }
                .doOnError { view?.displayError(it) }
                .subscribe()
    }

    override fun detach() {
        this.view = null
    }
}