package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.entities.FlashCardEntity
import kotlinx.coroutines.flow.Flow

interface IBoardsRepository {
    fun getBoardsFlow() : Flow<List<BoardEntity>>
    suspend fun getBoards() : List<BoardEntity>
    suspend fun getBoard(id: String) : BoardEntity?
    fun getBoardFlow(id: String) : Flow<BoardEntity?>
    suspend fun saveBoard(board: BoardEntity)
    suspend fun saveFlashCard(card: FlashCardEntity)
    suspend fun deleteBoard(boardId: String)
}