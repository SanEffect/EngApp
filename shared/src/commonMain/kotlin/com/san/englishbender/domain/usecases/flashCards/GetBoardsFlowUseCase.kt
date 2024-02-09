package com.san.englishbender.domain.usecases.flashCards

import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.repositories.IBoardsRepository
import kotlinx.coroutines.flow.Flow

class GetBoardsFlowUseCase(
    private val boardsRepository: IBoardsRepository
) {
    operator fun invoke(): Flow<List<BoardEntity>> = boardsRepository.getBoardsFlow()
}