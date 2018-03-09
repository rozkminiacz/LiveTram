package me.rozkmin.livetram.data

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmQuery

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
abstract class RealmDao<POJO, REALM_OBJ : RealmObject>(val realmConfig: RealmConfiguration,
                                                       val toRealmMapper: Mapper<POJO, REALM_OBJ>,
                                                       val toPojoMapper: Mapper<REALM_OBJ, POJO>,
                                                       val realmClass: Class<REALM_OBJ>) : BaseDao<POJO> {

    override fun add(item: POJO) {
        Realm.getInstance(realmConfig).use {
            it.executeTransaction {
                it.insertOrUpdate(toRealmMapper.map(item))
            }
        }
    }

    override fun addAll(items: List<POJO>) {
        Realm.getInstance(realmConfig).use {
            it.executeTransaction {
                it.insertOrUpdate(items.map {
                    toRealmMapper.map(it)
                })
            }
        }

    }

    override fun findAll(): List<POJO> {
        Realm.getInstance(realmConfig).use {
            return it.where(realmClass).findAll().map {
                toPojoMapper.map(it)
            }
        }
    }

    override fun findById(id: String): POJO {
        Realm.getInstance(realmConfig)
                .use {
                    val realmObj = it.where(realmClass).equalTo("id", id).findFirst()
                    if (realmObj == null) throw NoSuchElementException("Object with id $id not found")
                    else return toPojoMapper.map(realmObj)
                }
    }

    override fun removeAll() {
        Realm.getInstance(realmConfig).use {
            it.executeTransaction {
                it.delete(realmClass)
            }
        }
    }

    override fun remove(specification: Specification) {}
    abstract override fun query(specification: Specification): List<POJO>

    protected fun <T> RealmQuery<T>.inValues(key: String, values: List<String>): RealmQuery<T> where T : RealmObject {
        return values.fold(equalTo(key, ""), { query, value -> query.or().equalTo(key, value) })
    }
}

interface RealmSpecification<T : RealmObject> : Specification {
    fun query(realm: Realm): Iterable<T>
}