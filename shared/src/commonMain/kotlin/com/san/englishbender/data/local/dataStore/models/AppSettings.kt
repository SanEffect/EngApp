package com.san.englishbender.data.local.dataStore.models

import io.realm.kotlin.types.RealmObject


class AppSettings() : RealmObject {
    var isFirstLaunch: Boolean = false
}