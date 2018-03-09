package me.rozkmin.livetram.data.stops

import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import me.rozkmin.livetram.data.Mapper
import me.rozkmin.livetram.data.RealmDao
import me.rozkmin.livetram.data.Specification
import javax.inject.Inject

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
class StopDataDao @Inject constructor(
        realmConfiguration: RealmConfiguration,
        toStopDataMapper: ToStopDataMapper,
        toRealmStopDataMapper: ToRealmStopDataMapper) : RealmDao<StopData,
        RealmStopData>(
        realmConfiguration,
        toRealmStopDataMapper,
        toStopDataMapper,
        RealmStopData::class.java) {
    override fun query(specification: Specification): List<StopData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class ToStopDataMapper @Inject constructor() : Mapper<RealmStopData, StopData> {
    override fun map(from: RealmStopData): StopData {
        return from.let {
            StopData(
                    it.category,
                    it.id,
                    it.latitude,
                    it.longitude,
                    it.name,
                    it.shortName
            )
        }
    }
}

class ToRealmStopDataMapper @Inject constructor() : Mapper<StopData, RealmStopData> {
    override fun map(from: StopData): RealmStopData {
        return from.let {
            RealmStopData(
                    it.id,
                    it.category,
                    it.latitude,
                    it.longitude,
                    it.name,
                    it.shortName
            )
        }
    }

}

@RealmClass
open class RealmStopData(
        var id: String = "",
        var category: String = "",
        var latitude: Long = 0,
        var longitude: Long = 0,
        var name: String = "",
        var shortName: String = ""
) : RealmObject()