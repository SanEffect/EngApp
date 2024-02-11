package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.models.AppSettings
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.data.local.models.toEntity
import com.san.englishbender.data.local.models.toLocal
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.repositories.ITagsRepository
import com.san.englishbender.ioDispatcher
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class TagsRepository(
    private val realm: Realm,
//    private val dataStore: IDataStore
) : ITagsRepository {

//    val tags = realm
//        .query<Tag>()
//        .asFlow()
//        .map { result -> result.list.toList().toEntity() }
//        .flowOn(ioDispatcher)

    override fun getAllTagsFlow(): Flow<List<TagEntity>> = flow {
        realm.query(Tag::class).asFlow().collect { changes ->
            when (changes) {
                is InitialResults,
                is UpdatedResults -> emit(changes.list.toList().toEntity())
                else -> {}
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun getAllTags(): List<TagEntity> = doQuery {
        realm.query(Tag::class).find().map { it.toEntity() }
    }

    override suspend fun saveTag(tag: TagEntity): Unit = doQuery {
        realm.write { copyToRealm(tag.toLocal(), UpdatePolicy.ALL) }
    }

    override suspend fun saveTagColor(hexCode: String): Unit = doQuery {
        realm.writeBlocking {
            val appSettings = realm.query<AppSettings>().first().find() ?: return@writeBlocking
            appSettings.colorPresets.add(hexCode)

            val newAppSettings = appSettings.copyFromRealm()
            newAppSettings.colorPresets.add(hexCode)
            copyToRealm(newAppSettings, UpdatePolicy.ALL)

//            log(tag = "PrepopulateException") { "colorPresets: ${it.colorPresets}" }
//            val newAppSettings = it.copyFromRealm()
//            newAppSettings.colorPresets.add(hexCode)
//            log(tag = "PrepopulateException") { "new colorPresets: ${newAppSettings.colorPresets}" }
//            copyToRealm(newAppSettings, UpdatePolicy.ALL)
        }
    }

    override suspend fun deleteTag(tagId: String): Unit = doQuery {
        realm.write {
            val tag = query<Tag>("id == $0", tagId).find()
            delete(tag)
        }
    }
}