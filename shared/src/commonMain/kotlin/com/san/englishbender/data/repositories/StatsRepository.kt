package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.domain.repositories.IStatsRepository
import com.san.englishbender.ioDispatcher
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class StatsRepository(
    private val realm: Realm
) : IStatsRepository {

    override fun getAllStats(): Flow<Stats?> = flow  {
        realm.query<Stats>().first().asFlow().collect { changes: SingleQueryChange<Stats> ->
            when (changes) {
                is InitialResults<*> -> emit(changes.obj)
                is UpdatedResults<*> -> emit(changes.obj)
                else -> {}
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun updateStats(stats: Stats): Unit = doQuery {
        realm.write {
            copyToRealm(stats)
        }
    }

    override suspend fun deleteStats(): Unit = doQuery {
        val stats = realm.query<Stats>().find()
        realm.write {
            delete(stats)
        }
    }
}