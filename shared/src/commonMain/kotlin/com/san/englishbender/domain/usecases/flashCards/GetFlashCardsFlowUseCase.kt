package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.data.repositories.FlashCardsRepository
import com.san.englishbender.domain.entities.FlashCardEntity
import kotlinx.coroutines.flow.Flow

class GetFlashCardsFlowUseCase(
    private val flashCardsRepository: FlashCardsRepository
) {
    operator fun invoke(): Flow<List<FlashCardEntity>> = flashCardsRepository.getFlashCardsFlow()
}