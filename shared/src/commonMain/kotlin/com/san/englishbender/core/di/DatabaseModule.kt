package com.san.englishbender.core.di

import com.san.englishbender.data.local.dataStore.DataStoreRealm
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.models.AppSettings
import com.san.englishbender.data.local.models.Record
import com.san.englishbender.data.local.models.RecordTagRef
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.data.local.models.UserSettings
import com.san.englishbender.data.repositories.RecordTagRefRepository
import com.san.englishbender.data.repositories.RecordsRepository
import com.san.englishbender.data.repositories.StatsRepository
import com.san.englishbender.data.repositories.TagsRepository
import com.san.englishbender.domain.repositories.IRecordTagRefRepository
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.domain.repositories.IStatsRepository
import com.san.englishbender.domain.repositories.ITagsRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module


private val dataStoreModels = setOf(
    AppSettings::class,
    UserSettings::class,
    Record::class,
    Tag::class,
    RecordTagRef::class,
    Stats::class,
)

val databaseModule = module {

    single<IDataStore> { DataStoreRealm(get()) }

    single {
        val config = RealmConfiguration.create(schema = dataStoreModels)
        Realm.open(config)
    }

    single<IRecordsRepository> { RecordsRepository(get()) }
    single<IRecordTagRefRepository> { RecordTagRefRepository(get()) }
    single<ITagsRepository> { TagsRepository(get()) }
    single<IStatsRepository> { StatsRepository(get()) }
}