package com.san.englishbender.domain.usecases.labels

import com.san.englishbender.data.Result
import com.san.englishbender.domain.interactor.UseCase
import com.san.englishbender.domain.repositories.ILabelsRepository
import database.Label
import kotlinx.coroutines.flow.Flow


class SaveLabelUseCase(
    private val labelRepository: ILabelsRepository
) : UseCase<Unit, SaveLabelUseCase.Params> {

    data class Params(val label: Label)

    override suspend fun invoke(params: Params): Flow<Result<Unit>> {
        return labelRepository.saveLabel(params.label)
    }
}