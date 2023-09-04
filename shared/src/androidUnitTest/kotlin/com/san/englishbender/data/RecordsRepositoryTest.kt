package com.san.englishbender.data

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.san.englishbender.data.local.dataSources.RecordsDataSource
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.mappers.toLocal
import com.san.englishbender.data.repositories.RecordsRepository
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.randomUUID
import database.Record
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class RecordsRepositoryTest {

    @MockK
    lateinit var recordsDataSource: RecordsDataSource

    private lateinit var recordsRepository: RecordsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        recordsRepository = RecordsRepository(recordsDataSource)
    }

    @Test
    fun getRecordsStream() = runTest {
        val record = Record(
            id = "1",
            title = "Record",
            description = "Desc",
            creationDate = 0L,
            isDeleted = false,
            isDraft = false,
            backgroundColor = ""
        )

        every { recordsDataSource.getRecordsStream() } returns flowOf(listOf(record))

        recordsRepository.getRecordsStream().test {
            assertThat(awaitItem()).isEqualTo(listOf(record.toEntity()))
            cancelAndConsumeRemainingEvents()

            verify { recordsDataSource.getRecordsStream() }
        }
    }

    @Test
    fun getRecordById() = runTest {
        val record = Record(
            id = "1",
            title = "Record",
            description = "Desc",
            creationDate = 0L,
            isDeleted = false,
            isDraft = false,
            backgroundColor = ""
        )
        coEvery { recordsDataSource.getRecordById("1") } returns record

        val result = recordsRepository.getRecordById("1", false)

        coVerify { recordsDataSource.getRecordById("1") }

        MatcherAssert.assertThat(
            "Received result [$result] & mocked [$record] must be matches on each other!",
            result,
            CoreMatchers.`is`(record.toEntity())
        )
    }

    @Test
    fun insertRecord() = runTest {

        val newRecordEntity = RecordEntity(
            id = randomUUID(),
            title = "New Record Title",
            description = "Desc",
        )
        coEvery { recordsDataSource.insertRecord(newRecordEntity.toLocal()) } returns newRecordEntity.id

        val result = recordsRepository.saveRecord(newRecordEntity)

        coVerify { recordsRepository.saveRecord(newRecordEntity) }
        assertEquals(result, newRecordEntity.id)
    }

    @Test
    fun updateRecord() = runTest {

        val newRecordEntity = RecordEntity(
            id = randomUUID(),
            title = "Title",
            description = "Desc",
        )
        coEvery { recordsDataSource.updateRecord(newRecordEntity.toLocal()) } returns newRecordEntity.id

        val result = recordsRepository.saveRecord(newRecordEntity)

        coVerify { recordsRepository.saveRecord(newRecordEntity) }
        assertEquals(result, newRecordEntity.id)
    }

    @Test
    fun getRecordsCount() = runTest {
        val count = 0L
        every { recordsDataSource.getRecordsCount() } returns flowOf(count)

         recordsRepository.getRecordsCount().test {
             assertThat(awaitItem()).isEqualTo(count)
             cancelAndConsumeRemainingEvents()

             verify { recordsDataSource.getRecordsCount() }
         }
    }
}