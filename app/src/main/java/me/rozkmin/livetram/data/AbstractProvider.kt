package me.rozkmin.livetram.data

import io.reactivex.Single
import me.rozkmin.livetram.network.NetworkService


/**
 * Created by jaroslawmichalik on 20.01.2018
 */
abstract class AbstractProvider<T>(var networkService: NetworkService, var baseDao: BaseDao<T> = EmptyBaseDao()) {
    private val TAG = javaClass.simpleName

    abstract fun getOne(id: String): Single<T>
    abstract fun getBy(specification: Specification): Single<List<T>>
    abstract fun getAll(): Single<List<T>>

    open fun create(t: T): Single<T> = Single.never()
    open fun update(t: T): Single<T> = Single.never()

    internal fun Single<T>.saveOnSuccess() = doOnSuccess { baseDao.add(it) }

    internal fun Single<List<T>>.saveListOnSuccess() = doOnSuccess {
        baseDao.removeAll()
        baseDao.addAll(it)
    }

    internal fun Single<List<T>>.cachedOnError() = onErrorReturn {
        baseDao.findAll()
    }

    internal fun Single<T>.cachedOnError(id: String) = onErrorReturn {
        baseDao.findById(id)
    }

    open fun <T> Single<T>.executePendingOnSuccess(): Single<T> = Single.never()

    class EmptyBaseDao<T> : BaseDao<T> {
        override fun add(item: T) = throw Exception()

        override fun addAll(items: List<T>) = throw Exception()


        override fun findAll(): List<T> = throw Exception()

        override fun findById(id: String): T = throw Exception()

        override fun remove(specification: Specification) = throw Exception()

        override fun removeAll() = throw Exception()

        override fun query(specification: Specification): List<T> = throw Exception()

    }
}
