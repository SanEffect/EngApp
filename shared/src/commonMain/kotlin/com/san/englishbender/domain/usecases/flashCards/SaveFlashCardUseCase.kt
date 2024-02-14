package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository

class SaveFlashCardUseCase(private val boardRepository: IBoardsRepository) {
    suspend operator fun invoke(card: FlashCardEntity) = boardRepository.saveFlashCard(card)
}