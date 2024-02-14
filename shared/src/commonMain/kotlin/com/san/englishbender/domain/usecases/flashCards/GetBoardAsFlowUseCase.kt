package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository
import kotlinx.coroutines.flow.Flow

class GetBoardAsFlowUseCase(
    private val boardRepository: IBoardsRepository
) {
    operator fun invoke(boardId: String): Flow<BoardEntity?> =
        boardRepository.getBoardAsFlow(boardId)
}