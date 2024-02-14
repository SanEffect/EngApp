package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository

class AddFlashCardToBoardUseCase(private val boardRepository: IBoardsRepository) {
    suspend operator fun invoke(boardId: String, flashCardEntity: FlashCardEntity) =
        boardRepository.addFlashCardToBoard(boardId, flashCardEntity)
}