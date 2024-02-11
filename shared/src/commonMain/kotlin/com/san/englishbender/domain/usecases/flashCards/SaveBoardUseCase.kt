package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository

class SaveBoardUseCase(private val boardRepository: IBoardsRepository) {
    suspend operator fun invoke(board: BoardEntity) = boardRepository.saveBoard(board)
}