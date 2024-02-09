package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.FlashCardEntity
import kotlinx.coroutines.flow.Flow

interface IFlashCardsRepository {
    fun getFlashCardsFlow() : Flow<List<FlashCardEntity>>
    suspend fun getFlashCards() : List<FlashCardEntity>
    suspend fun saveFlashCard(card: FlashCardEntity)
    suspend fun deleteFlashCard(cardId: String)
}