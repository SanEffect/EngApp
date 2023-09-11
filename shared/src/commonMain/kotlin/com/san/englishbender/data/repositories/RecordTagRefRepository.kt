package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.models.RecordTagRef
import com.san.englishbender.domain.repositories.IRecordTagRefRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class RecordTagRefRepository(
    private val realm: Realm
) : IRecordTagRefRepository {

    override suspend fun getAllRecordTagRef(): List<RecordTagRef> = doQuery {
        realm.query(RecordTagRef::class).find()
    }

    override suspend fun saveRecordTagRef(recordTagRef: RecordTagRef): Unit =
        doQuery {
            realm.write { copyToRealm(recordTagRef) }
        }

    override suspend fun deleteByRecordId(recordId: String): Unit =
        doQuery {
            val recordTagRefs = realm.query<RecordTagRef>("recordId == $0", recordId).find()
            realm.write {
                delete(recordTagRefs)
            }
        }

    override suspend fun deleteByTagId(tagId: String): Unit =
        doQuery {
            val recordTagRefs = realm.query<RecordTagRef>("tagId == $0", tagId).find()
            realm.write {
                delete(recordTagRefs)
            }
        }

    override suspend fun deleteByRecordTagRefId(recordId: String, tagId: String): Unit =
        doQuery {
            val where = "recordId == $0 AND tagId == $1"
            val recordTagRefs = realm.query<RecordTagRef>(where, recordId, tagId).find()
            realm.write {
                delete(recordTagRefs)
            }
        }
}
