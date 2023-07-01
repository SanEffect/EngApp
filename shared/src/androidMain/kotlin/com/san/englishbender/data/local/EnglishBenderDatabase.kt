package com.san.englishbender.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.san.englishbender.data.local.dao.RecordsDao
import com.san.englishbender.data.local.dto.RecordDto

@Database(
    entities = [
        RecordDto::class,
    ], version = 1, exportSchema = false
)
//@TypeConverters(WordConverters::class)
abstract class EnglishBenderDatabase : RoomDatabase() {

    abstract val recordsDao: RecordsDao

    companion object {

        private const val DATABASE_NAME = "english_bender"

        // For Singleton instantiation
        @Volatile private var instance: EnglishBenderDatabase? = null

        fun getInstance(context: Context): EnglishBenderDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): EnglishBenderDatabase {
            return Room.databaseBuilder(
                context, EnglishBenderDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}