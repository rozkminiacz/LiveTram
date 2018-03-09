package me.rozkmin.livetram.data.di

import dagger.Module
import dagger.Provides
import me.rozkmin.livetram.data.BaseDao
import me.rozkmin.livetram.data.stops.StopData
import me.rozkmin.livetram.data.stops.StopDataDao

/**
 * Created by jaroslawmichalik on 02.03.2018
 */
@Module
class DatabaseModule {
    @Provides
    fun provideStopsDao(dao : StopDataDao) : BaseDao<StopData> = dao
}