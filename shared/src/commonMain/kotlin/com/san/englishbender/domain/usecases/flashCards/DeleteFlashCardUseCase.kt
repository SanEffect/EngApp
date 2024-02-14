package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.repositories.IBoardsRepository

class DeleteFlashCardUseCase(
    private val boardRepository: IBoardsRepository
) {
    suspend operator fun invoke(cardId: String): Unit =
        boardRepository.deleteFlashCard(cardId)
}