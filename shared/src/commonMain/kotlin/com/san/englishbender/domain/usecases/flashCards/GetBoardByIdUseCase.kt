package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository

class GetBoardByIdUseCase(
    private val boardRepository: IBoardsRepository
) {
    suspend operator fun invoke(boardId: String): BoardEntity? =
        boardRepository.getBoard(boardId)
}