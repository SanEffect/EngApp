package com.san.englishbender.records

import com.google.common.truth.Truth.assertThat
import com.san.englishbender.data.Result
import com.san.englishbender.data.TestRecordRepository
import com.san.englishbender.data.TestStatsRepository
import com.san.englishbender.data.asSuccess
import com.san.englishbender.data.succeeded
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.domain.usecases.stats.GetStatsUseCase
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import com.san.englishbender.ui.records.RecordsUiState
import com.san.englishbender.ui.records.RecordsViewModel
import com.san.englishbender.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecordsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var testDispatcher = UnconfinedTestDispatcher()

    private val recordsRepository = TestRecordRepository()
    private val statsRepository = TestStatsRepository()

    private val getRecordsUseCase = GetRecordsUseCase(
        recordsRepository = recordsRepository
    )
    private val getStatsUseCase = GetStatsUseCase(
        statsRepository = statsRepository
    )
    private val updateStatsUseCase = UpdateStatsUseCase(
        statsRepository = statsRepository,
        getStatsUseCase = getStatsUseCase
    )
    private val removeRecordUseCase = RemoveRecordUseCase(
        recordsRepository = recordsRepository,
        updateStatsUseCase = updateStatsUseCase
    )

    private lateinit var recordsViewModel: RecordsViewModel

    @Before
    fun setup() {
        recordsViewModel = RecordsViewModel(
            getRecordsUseCase = getRecordsUseCase,
            removeRecordUseCase = removeRecordUseCase
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(
            RecordsUiState(isLoading = true),
            recordsViewModel.recordsUiState.value
        )
    }

    @Test
    fun loadRecords() = runTest {
        val collectJob = launch(testDispatcher) {
            recordsViewModel.recordsUiState.collect()
        }

//        recordsRepository.sendRecords(samplesRecords)
        recordsRepository.addRecords(samplesRecords)

        assertEquals(
            RecordsUiState(
                records = listOf(
                    RecordEntity(
                        id = "0",
                        title = "Record_0",
                        description = "Desc_0",
                        isDeleted = false,
                        isDraft = false,
                        creationDate = 0L,
                        backgroundColor = "#FFFFFF",
                        labels = listOf()
                    ),
                    RecordEntity(
                        id = "1",
                        title = "Record_1",
                        description = "Desc_1",
                        isDeleted = false,
                        isDraft = false,
                        creationDate = 0L,
                        backgroundColor = "#FFFFFF",
                        labels = listOf()
                    ),
                    RecordEntity(
                        id = "2",
                        title = "Record_2",
                        description = "Desc_2",
                        isDeleted = false,
                        isDraft = false,
                        creationDate = 0L,
                        backgroundColor = "#FFFFFF",
                        labels = listOf()
                    ),
                )
            ),
            recordsViewModel.recordsUiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun removeRecord() = runTest {

        recordsRepository.getRecords(true).let { result ->
            if (result.succeeded) {

            }
        }

//        val recordsSize = (recordsRepository.getRecords(true) as Result.Success).data.size
        val recordsSize = recordsRepository.getRecords(true).asSuccess()?.size ?: 0

        recordsRepository.removeRecord(samplesRecords.first().id)

        val afterDeleteRecord = (recordsRepository.getRecords(true) as Result.Success).data

        assertThat(afterDeleteRecord.size).isEqualTo(recordsSize - 1)
        assertThat(afterDeleteRecord).doesNotContain(samplesRecords.first())
    }
}

val samplesRecords = listOf(
    RecordEntity(
        id = "0",
        title = "Record_0",
        description = "Desc_0",
        isDeleted = false,
        isDraft = false,
        creationDate = 0L,
        backgroundColor = "#FFFFFF",
        labels = listOf()
    ),
    RecordEntity(
        id = "1",
        title = "Record_1",
        description = "Desc_1",
        isDeleted = false,
        isDraft = false,
        creationDate = 0L,
        backgroundColor = "#FFFFFF",
        labels = listOf()
    ),
    RecordEntity(
        id = "2",
        title = "Record_2",
        description = "Desc_2",
        isDeleted = false,
        isDraft = false,
        creationDate = 0L,
        backgroundColor = "#FFFFFF",
        labels = listOf()
    ),
)