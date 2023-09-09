package com.san.englishbender.data.local.models

import io.realm.kotlin.types.RealmObject


class AppSettings() : RealmObject {
    var isFirstLaunch: Boolean = false
}