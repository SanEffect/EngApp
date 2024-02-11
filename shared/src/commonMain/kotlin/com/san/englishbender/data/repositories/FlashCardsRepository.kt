package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.models.FlashCard
import com.san.englishbender.data.local.models.toEntity
import com.san.englishbender.data.local.models.toLocal
import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.domain.repositories.IFlashCardsRepository
import com.san.englishbender.ioDispatcher
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FlashCardsRepository(
    private val realm: Realm
): IFlashCardsRepository {
    override fun getFlashCardsFlow(boardId: String): Flow<List<FlashCardEntity>> = flow {
        realm.query<FlashCard>("").asFlow().collect { changes ->
            when (changes) {
                is InitialResults,
                is UpdatedResults -> emit(changes.list.toList().toEntity())
                else -> {}
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun getFlashCards(): List<FlashCardEntity> = doQuery {
        realm.query(FlashCard::class).find().map { it.toEntity() }
    }

//    override suspend fun saveFlashCard(card: FlashCardEntity): Unit = doQuery {
//        realm.write { copyToRealm(card.toLocal(), UpdatePolicy.ALL) }
//    }

    override suspend fun deleteFlashCard(cardId: String): Unit = doQuery {
        realm.write {
            val card = query<FlashCard>("id == $0", cardId).find()
            delete(card)
        }
    }
}