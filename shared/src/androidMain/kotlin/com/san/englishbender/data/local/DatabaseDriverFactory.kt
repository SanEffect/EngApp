package com.san.englishbender.data.local

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.san.englishbender.database.EngAppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory constructor(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = EngAppDatabase.Schema,
            context = context,
            name = "EngAppDatabase.db",
            callback = object : AndroidSqliteDriver.Callback(EngAppDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.execSQL("PRAGMA foreign_keys=ON;")
                }
            }
        )
    }
}