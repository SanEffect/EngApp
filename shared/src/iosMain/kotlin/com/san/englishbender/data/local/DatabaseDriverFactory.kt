package com.san.englishbender.data.local

import com.san.englishbender.database.EngAppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(EngAppDatabase.Schema, "EngAppDatabase.db")
    }
}