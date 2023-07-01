package com.san.englishbender.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.san.englishbender.domain.entities.Record

@Entity(tableName = "records")
data class RecordDto(
    @PrimaryKey
//    var id: String = UUID.randomUUID().toString(),
    var id: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "text")
    var description: String = "",

    @ColumnInfo(name = "creation_date")
//    var creationDate: Long = System.currentTimeMillis(),
    var creationDate: Long = 0,

    @ColumnInfo(name = "is_deleted")
    var isDeleted: Boolean = false,

    @ColumnInfo(name = "is_draft")
    var isDraft: Boolean = false,

    @ColumnInfo(name = "background_color")
    var backgroundColor: String = "",
)

fun RecordDto.toEntity() = Record(
    this.title,
    this.description,
    this.id,
    this.isDeleted,
    this.isDraft,
    this.creationDate,
    this.backgroundColor,
)

fun Collection<RecordDto>.toEntities() : List<Record> = this.map { it.toEntity() }

fun Record.toDto() = RecordDto(
    title = this.title,
    description = this.description,
    creationDate = this.creationDate,
    isDeleted = this.isDeleted,
    isDraft = this.isDraft,
    backgroundColor = this.backgroundColor,
    id = this.id
)
