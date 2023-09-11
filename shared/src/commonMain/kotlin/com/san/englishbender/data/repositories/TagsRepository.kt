package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.repositories.ITagsRepository
import com.san.englishbender.ioDispatcher
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TagsRepository(
    private val realm: Realm
) : ITagsRepository {

    override fun getAllTagsFlow(): Flow<List<TagEntity>> = flow {
        log(tag = "showAllRecordTagRef") { "getAllTagsFlow" }
        realm.query(Tag::class).asFlow().collect { changes ->
            log(tag = "showAllRecordTagRef") { "changes: $changes" }
            when (changes) {
                is InitialResults,
                is UpdatedResults -> {
                    log(tag = "showAllRecordTagRef") { "list: ${changes.list.toList()}" }
                    emit(changes.list.toList().toEntity())
                }
                else -> {}
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun getAllTags(): List<TagEntity> = doQuery {
        realm.query(Tag::class).find().map { it.toEntity() }
    }

    override suspend fun saveTag(tag: Tag): Unit = doQuery {
        realm.write { copyToRealm(tag) }
    }

    override suspend fun deleteTag(tagId: String): Unit = doQuery {
        val tag = realm.query<Tag>("id == $0", tagId).first()
        realm.write { delete(tag) }
    }
}