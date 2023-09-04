package com.san.englishbender.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.domain.usecases.stats.GetStatsUseCase
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import com.san.englishbender.ui.records.RecordsUiState
import com.san.englishbender.ui.records.RecordsViewModel
import com.san.englishbender.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RecordsViewModelTest {
//    @get:Rule
//    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private var testDispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var getRecordsUseCase: GetRecordsUseCase

    @MockK
    lateinit var getStatsUseCase: GetStatsUseCase

    @MockK
    lateinit var updateStatsUseCase: UpdateStatsUseCase

    @MockK
    lateinit var removeRecordUseCase: RemoveRecordUseCase

    // Subject under test
    private lateinit var recordsViewModel: RecordsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {

        every { getRecordsUseCase() } returns flowOf(samplesRecords)

        recordsViewModel = RecordsViewModel(
            getRecordsUseCase = getRecordsUseCase,
            removeRecordUseCase = removeRecordUseCase
        )

        assertEquals(
            RecordsUiState(isLoading = true),
            recordsViewModel.uiState.value
        )
    }

    @Test
    fun loadRecords() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        every { getRecordsUseCase() } returns flowOf(samplesRecords)

        recordsViewModel = RecordsViewModel(
            getRecordsUseCase = getRecordsUseCase,
            removeRecordUseCase = removeRecordUseCase
        )

        recordsViewModel.uiState.test {
            assertEquals(awaitItem(), RecordsUiState(isLoading = true))

            assertThat(awaitItem()).isEqualTo(
                RecordsUiState(
                    isLoading = false,
                    records = samplesRecords
                )
            )

            cancelAndConsumeRemainingEvents()
            verify { getRecordsUseCase() }
        }
    }

    @Test
    fun getRecordsUseCaseUnitTest() = runTest {

//        every { recordsRepository.getRecordsStream() } returns flowOf(samplesRecords)

//        val res = getRecordsUseCase().first()
//        println("res: $res")

        assertEquals(5, 2 + 3)

//        getRecordsUseCase().test {
//            val item = awaitItem()
//            println("item: $item")
//
//            cancelAndConsumeRemainingEvents()
//            verify { getRecordsUseCase() }
//        }
    }

    class TestRepository<T> {
        val flow = MutableSharedFlow<T>()

        suspend fun emit(value: T) {
            flow.emit(value)
        }
    }

//    @Test
//    fun removeRecord() = runTest {
//
//        recordsRepository.getRecords(true).let { result ->
//            if (result.succeeded) {
//
//            }
//        }
//
////        val recordsSize = (recordsRepository.getRecords(true) as Result.Success).data.size
//        val recordsSize = recordsRepository.getRecords(true).asSuccess()?.size ?: 0
//
//        recordsRepository.removeRecord(samplesRecords.first().id)
//
//        val afterDeleteRecord = (recordsRepository.getRecords(true) as Result.Success).data
//
//        assertThat(afterDeleteRecord.size).isEqualTo(recordsSize - 1)
//        assertThat(afterDeleteRecord).doesNotContain(samplesRecords.first())
//    }
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