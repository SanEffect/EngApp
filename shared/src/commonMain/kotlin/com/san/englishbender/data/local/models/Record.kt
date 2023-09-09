package com.san.englishbender.data.local.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Record : RealmObject {
    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var description: String = ""
    var isDraft: Boolean = false
    var isDeleted: Boolean = false
    var creationDate: Long = 0
    var backgroundColor: String = ""
    var tags: RealmList<RecordTagRef> = realmListOf()

    constructor(
        id: String,
        title: String,
        description: String,
        isDraft: Boolean = false,
        isDeleted: Boolean = false,
        creationDate: Long = 0,
        backgroundColor: String = "",
        tags: RealmList<RecordTagRef>
    ) {
        this.id = id
        this.title = title
        this.description = description
        this.isDraft = isDraft
        this.isDeleted = isDeleted
        this.creationDate = creationDate
        this.backgroundColor = backgroundColor
        this.tags = tags
    }

    constructor() {}
}
