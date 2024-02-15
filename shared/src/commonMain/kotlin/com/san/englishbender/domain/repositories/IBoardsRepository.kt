package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.entities.FlashCardEntity
import kotlinx.coroutines.flow.Flow

interface IBoardsRepository {
    fun getBoardsFlow() : Flow<List<BoardEntity>>
    suspend fun getBoards() : List<BoardEntity>
    suspend fun getBoard(id: String) : BoardEntity?
    fun getBoardAsFlow(id: String) : Flow<BoardEntity?>
    fun getFlashCardsAsFlow(id: String): Flow<List<FlashCardEntity?>>
//    suspend fun sendCardToArchive(boardId: String, cardId: String)
    suspend fun saveBoard(board: BoardEntity)
    suspend fun addFlashCardToBoard(boardId: String, flashCard: FlashCardEntity)
    suspend fun saveFlashCard(card: FlashCardEntity)
    suspend fun deleteBoard(boardId: String)
    suspend fun deleteFlashCard(cardId: String)
}