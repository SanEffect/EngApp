package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.core.extensions.toFlow
import com.san.englishbender.data.local.models.Board
import com.san.englishbender.data.local.models.toEntity
import com.san.englishbender.data.local.models.toLocal
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository
import com.san.englishbender.ioDispatcher
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class BoardsRepository(
    private val realm: Realm
): IBoardsRepository {
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

    override suspend fun saveBoard(board: BoardEntity): Unit = doQuery {
        val local = board.toLocal()
        log(tag = "containerColor") { "local: $local" }
        realm.write { copyToRealm(local, UpdatePolicy.ALL) }
    }

    override suspend fun deleteBoard(boardId: String): Unit = doQuery {
        realm.write {
            val board = query<Board>("id == $0", boardId).find()
            delete(board)
        }
    }
}