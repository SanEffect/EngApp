package com.san.englishbender.di

import androidx.room.Room
import com.san.englishbender.core.AppConstants
import com.san.englishbender.data.local.EnglishBenderDatabase
import com.san.englishbender.data.local.dao.RecordsDao
import com.san.englishbender.data.repositories.IRecordsRepository
import com.san.englishbender.data.repositories.RecordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {

    // Database
    single {
        Room.databaseBuilder(
            androidContext(), EnglishBenderDatabase::class.java,
            AppConstants.DATABASE_NAME
        )
//            .fallbackToDestructiveMigration()
            .build()
    }

    factory { Dispatchers.IO }

    // Dao
    single { EnglishBenderDatabase.getInstance(androidContext()).recordsDao }

    single<IRecordsRepository> { RecordsRepository(get()) }
}
