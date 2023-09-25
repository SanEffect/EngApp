package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.mappers.toLocal
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.domain.entities.StatsEntity
import com.san.englishbender.domain.repositories.IStatsRepository
import com.san.englishbender.ioDispatcher
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class StatsRepository(
    private val realm: Realm
) : IStatsRepository {

    override fun getStatsFlow(): Flow<StatsEntity?> = flow {
        realm.query<Stats>().first().asFlow().collect { changes: SingleQueryChange<Stats> ->
            emit(changes.obj?.toEntity())
        }
    }.flowOn(ioDispatcher)

    override suspend fun getStats(): StatsEntity? = doQuery {
        realm.query(Stats::class).first().find()?.toEntity()
    }

    override suspend fun updateStats(stats: StatsEntity): Unit = doQuery {
        realm.write { copyToRealm(stats.toLocal(), UpdatePolicy.ALL) }
    }

    override suspend fun deleteStats(): Unit = doQuery {
        val stats = realm.query<Stats>().find()
        realm.write { delete(stats) }
    }
}