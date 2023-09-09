package com.san.englishbender.data.local.models

import io.realm.kotlin.types.RealmObject

open class RecordTagRef : RealmObject {
    var recordId: String = ""
    var tagId: String = ""
}