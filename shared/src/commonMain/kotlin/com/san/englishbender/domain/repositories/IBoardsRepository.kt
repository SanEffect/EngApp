package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.BoardEntity
import kotlinx.coroutines.flow.Flow

interface IBoardsRepository {
    fun getBoardsFlow() : Flow<List<BoardEntity>>
    suspend fun getBoards() : List<BoardEntity>
    suspend fun saveBoard(board: BoardEntity)
    suspend fun deleteBoard(boardId: String)
}