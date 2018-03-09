package me.rozkmin.livetram.data.stops

import android.util.Log
import io.reactivex.Single
import me.rozkmin.livetram.data.AbstractProvider
import me.rozkmin.livetram.data.BaseDao
import me.rozkmin.livetram.data.Specification
import me.rozkmin.livetram.network.NetworkService
import javax.inject.Inject

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class StopDataProvider @Inject constructor(networkService: NetworkService, stopDataDao: BaseDao<StopData>) : AbstractProvider<StopData>(networkService, stopDataDao) {

    companion object {
        val TAG : String = StopDataProvider::class.java.simpleName
    }

    override fun getOne(id: String): Single<StopData> =
            Single.just(baseDao.findById(id))


    override fun getBy(specification: Specification): Single<List<StopData>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Single<List<StopData>> =
            networkService.getStops()
                    .onErrorReturn { emptyList() }
                    .doOnEvent { t1, t2 -> Log.d(TAG, "getAll: $t1, $t2") }
                    .saveListOnSuccess()
                    .cachedOnError()


}