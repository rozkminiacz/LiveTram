package me.rozkmin.livetram.data.tram

import io.reactivex.Single
import me.rozkmin.livetram.data.AbstractProvider
import me.rozkmin.livetram.data.Specification
import me.rozkmin.livetram.network.NetworkService
import javax.inject.Inject

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class TramLocationDataProvider @Inject constructor(networkService: NetworkService) : AbstractProvider<TramData>(networkService) {
    override fun getOne(id: String): Single<TramData> =
            getAll().map { it.first { it.id.contentEquals(id) } }

    override fun getBy(specification: Specification): Single<List<TramData>> = getAll()

    override fun getAll(): Single<List<TramData>> = networkService.getTrams()
            .onErrorReturn{ emptyList() }

}