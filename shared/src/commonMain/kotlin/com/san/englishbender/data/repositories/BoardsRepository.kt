package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.models.Board
import com.san.englishbender.data.local.models.FlashCard
import com.san.englishbender.data.local.models.toEntity
import com.san.englishbender.data.local.models.toLocal
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository
import com.san.englishbender.ioDispatcher
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.DeletedList
import io.realm.kotlin.notifications.InitialList
import io.realm.kotlin.notifications.InitialObject
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ListChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.notifications.UpdatedList
import io.realm.kotlin.notifications.UpdatedObject
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.query.find
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BoardsRepository(
    private val realm: Realm
) : IBoardsRepository {
    override fun getBoardsFlow(): Flow<List<BoardEntity>> = flow {
        realm.query(Board::class).asFlow().collect { changes ->
            when (changes) {
                is InitialResults,
                is UpdatedResults -> emit(changes.list.toList().toEntity())

                else -> {}
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun getBoards(): List<BoardEntity> = doQuery {
        realm.query(Board::class).find().map { it.toEntity() }
    }

    override suspend fun getBoard(id: String): BoardEntity? = doQuery {
        realm.query<Board>("id == $0", id).first().find()?.toEntity()
    }

    override fun getBoardAsFlow(id: String): Flow<BoardEntity?> = flow {
        realm.query<Board>("id == $0", id)
            .first()
            .asFlow()
            .collect { changes: SingleQueryChange<Board> ->
                when (changes) {
                    is InitialObject<*>,
                    is UpdatedObject<*> -> changes.obj?.toEntity()?.let { emit(it) }
                    else -> {}
                }
            }
    }.flowOn(ioDispatcher)

    override fun getFlashCardsAsFlow(id: String): Flow<List<FlashCardEntity?>> = flow {
        realm.query<Board>("id == $0", id)
            .first()
            .find()
            ?.also { board ->
                board.flashCards
                    .asFlow()
                    .collect { listChange: ListChange<FlashCard> ->
                    when (listChange) {
                        is InitialList,
                        is UpdatedList,
                        is DeletedList -> emit(
                            listChange.list.filter { !it.isArchived }.toEntity()
                        )
                    }
                }
            }
    }.flowOn(ioDispatcher)

    override suspend fun saveBoard(board: BoardEntity): Unit = doQuery {
        realm.write { copyToRealm(board.toLocal(), UpdatePolicy.ALL) }
    }

    override suspend fun addFlashCardToBoard(boardId: String, flashCard: FlashCardEntity): Unit =
        doQuery {
            realm.write {
                val board = this.query<Board>("id == $0", boardId).first().find()
                board?.flashCards?.add(flashCard.toLocal())
            }
        }

    override suspend fun saveFlashCard(card: FlashCardEntity): Unit = doQuery {
        realm.write { copyToRealm(card.toLocal(), UpdatePolicy.ALL) }
    }

//    override suspend fun sendCardToArchive(boardId: String, cardId: String): Unit =
//        doQuery {
//            realm.write {
//                val board = this.query<Board>("id == $0", boardId).first().find()
//                board?.flashCards?.find { it.id == cardId }?.isArchived = true
//            }
//        }

    override suspend fun deleteBoard(boardId: String): Unit = doQuery {
        realm.write {
            val board = query<Board>("id == $0", boardId).find()
            delete(board)
        }
    }

    override suspend fun deleteFlashCard(cardId: String): Unit = doQuery {
        realm.write {
            val card = query<FlashCard>("id == $0", cardId).find()
            delete(card)
        }
    }
}