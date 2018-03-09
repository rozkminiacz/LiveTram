package me.rozkmin.livetram.network

import io.reactivex.Single
import me.rozkmin.livetram.data.stops.StopData
import me.rozkmin.livetram.data.tram.TramData
import retrofit2.http.GET

/**
 * Created by jaroslawmichalik on 20.12.2017
 */
interface NetworkService {
    @GET("/stops")
    fun getStops(): Single<List<StopData>>

    @GET("/trams")
    fun getTrams(): Single<List<TramData>>
}