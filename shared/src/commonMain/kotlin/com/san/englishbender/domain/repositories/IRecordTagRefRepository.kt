package com.san.englishbender.domain.repositories

import com.san.englishbender.data.local.models.RecordTagRef

interface IRecordTagRefRepository {

    suspend fun getAllRecordTagRef(): List<RecordTagRef>
    suspend fun saveRecordTagRef(recordTagRef: RecordTagRef)
    suspend fun deleteByRecordId(recordId: String)
    suspend fun deleteByTagId(tagId: String)
    suspend fun deleteByRecordTagRefId(recordId: String, tagId: String)
}