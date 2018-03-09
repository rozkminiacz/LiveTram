package me.rozkmin.livetram.data
/**
 * Created by jaroslawmichalik on 20.12.2017
 */
interface BaseDao<T> {
    fun add(item: T)
    fun addAll(items: List<T>)
    fun findAll(): List<T>
    fun findById(id: String) : T
    fun remove(specification: Specification)
    fun removeAll()
    fun query(specification: Specification): List<T>
}

/**
 * Mapper
 * @param From - object to be mapped
 * @param To - target object
 */
interface Mapper<From, To> {
    fun map(from: From): To
}
