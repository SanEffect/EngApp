package com.san.englishbender.data.local.dataStore.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class UserSettings : RealmObject {
    var labelColors: RealmList<String> = realmListOf()
}