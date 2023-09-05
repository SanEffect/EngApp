package com.san.englishbender.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.san.englishbender.data.repositories.RecordsRepository
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.ui.samplesRecords
import com.san.englishbender.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetRecordsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainRule = MainDispatcherRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var recordsRepository: RecordsRepository

    // SUT
    private lateinit var getRecordsUseCase: GetRecordsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        getRecordsUseCase = GetRecordsUseCase(
            recordsRepository = recordsRepository
        )
    }

    @Test
    fun getRecordsUseCaseUnitTest() = runTest {

        every { recordsRepository.getRecordsFlow(false) } returns flowOf(samplesRecords)

        getRecordsUseCase().test {
            assertThat(awaitItem()).isEqualTo(samplesRecords)

            cancelAndConsumeRemainingEvents()
            verify { getRecordsUseCase() }
        }
    }
}