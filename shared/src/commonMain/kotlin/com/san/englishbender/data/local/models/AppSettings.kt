package com.san.englishbender.data.local.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class AppSettings : RealmObject {
    @PrimaryKey
    var id: String = "ee1dd7d0-00b5-4bc8-b816-51bdf9919d2a"
    var isFirstLaunch: Boolean = false
    var colorPresets: RealmList<String> = realmListOf()
}