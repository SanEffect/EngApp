package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository
import kotlinx.coroutines.flow.Flow

class GetFlashCardsAsFlowUseCase(
    private val boardRepository: IBoardsRepository
) {
    operator fun invoke(boardId: String): Flow<List<FlashCardEntity?>> =
        boardRepository.getFlashCardsAsFlow(boardId)
}