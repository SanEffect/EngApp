package com.san.englishbender.core.di

import com.san.englishbender.data.local.DatabaseDriverFactory
import com.san.englishbender.data.local.dataSources.IRecordsDataSource
import com.san.englishbender.data.local.dataSources.RecordsDataSource
import com.san.englishbender.data.repositories.RecordsRepository
import com.san.englishbender.database.EngAppDatabase
import com.san.englishbender.domain.repositories.IRecordsRepository
import org.koin.dsl.module


val databaseModule = module {

    single { Database(get()) }

    single<IRecordsDataSource> { RecordsDataSource(get()) }
    single<IRecordsRepository> { RecordsRepository(get()) }
}

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = EngAppDatabase(databaseDriverFactory.createDriver())
    val dbQueries = database.engAppDatabaseQueries
}