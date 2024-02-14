package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.repositories.IBoardsRepository

class DeleteBoardUseCase(
    private val boardRepository: IBoardsRepository
) {
    suspend operator fun invoke(boardId: String): Unit =
        boardRepository.deleteBoard(boardId)
}