package com.san.englishbender.data.local

import android.content.Context
import com.san.englishbender.database.EngAppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory constructor(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(EngAppDatabase.Schema, context, "EngAppDatabase.db")
    }
}