package com.san.englishbender.core.di

import com.san.englishbender.data.local.DatabaseDriverFactory
import com.san.englishbender.data.local.dataSources.ILabelsDataSource
import com.san.englishbender.data.local.dataSources.IRecordsDataSource
import com.san.englishbender.data.local.dataSources.IStatsDataSource
import com.san.englishbender.data.local.dataSources.LabelsDataSource
import com.san.englishbender.data.local.dataSources.RecordsDataSource
import com.san.englishbender.data.local.dataSources.StatsDataSource
import com.san.englishbender.data.local.dataStore.DataStore
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.dataStore.models.AppSettings
import com.san.englishbender.data.repositories.LabelsRepository
import com.san.englishbender.data.repositories.RecordsRepository
import com.san.englishbender.data.repositories.StatsRepository
import com.san.englishbender.database.EngAppDatabase
import com.san.englishbender.domain.repositories.ILabelsRepository
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.domain.repositories.IStatsRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module


private val dataStoreModels = setOf(
    AppSettings::class
)

val databaseModule = module {

    single { Database(get()) }

    single<IRecordsDataSource> { RecordsDataSource(get()) }
    single<IRecordsRepository> { RecordsRepository(get()) }

    single<IStatsDataSource> { StatsDataSource(get()) }
    single<IStatsRepository> { StatsRepository(get()) }

    single<ILabelsDataSource> { LabelsDataSource(get()) }
    single<ILabelsRepository> { LabelsRepository(get()) }

    single {
        val config = RealmConfiguration.create(schema = dataStoreModels)
        Realm.open(config)
    }

    single<IDataStore> { DataStore(get()) }
}

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = EngAppDatabase(databaseDriverFactory.createDriver())
    val dbQueries = database.engAppDatabaseQueries
}