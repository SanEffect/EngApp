package com.san.englishbender.data

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.mappers.toLocal
import com.san.englishbender.data.repositories.RecordsRepository
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.randomUUID
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import com.san.englishbender.data.local.models.Record
import io.realm.kotlin.ext.realmListOf


class RecordsRepositoryTest {

    @MockK
    lateinit var realm: Realm

    private lateinit var recordsRepository: RecordsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        recordsRepository = RecordsRepository(realm)
    }

    @Test
    fun getRecordsFlow() = runTest {
        val record = Record(
            id = "1",
            title = "Record",
            description = "Desc",
            creationDate = 0L,
            isDeleted = false,
            isDraft = false,
            backgroundColor = "",
            tags = realmListOf()
        )

//        every { recordsDataSource.getRecordsFlow() } returns flowOf(listOf(record))

        recordsRepository.getRecordsFlow(false).test {
            assertThat(awaitItem()).isEqualTo(listOf(record.toEntity()))
            cancelAndConsumeRemainingEvents()

            verify { recordsRepository.getRecordsFlow(false) }
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
            backgroundColor = "",
            tags = realmListOf()
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
        val record = newRecordEntity.toLocal()
        coEvery { recordsDataSource.insertRecord(record) } returns newRecordEntity.id

//        val result = recordsRepository.saveRecord(newRecordEntity)
        val result = recordsRepository.insertRecord(newRecordEntity)

        coVerify { recordsDataSource.insertRecord(record) }
        assertEquals(result, newRecordEntity.id)
    }

    @Test
    fun updateRecord() = runTest {

        val newRecordEntity = RecordEntity(
            id = randomUUID(),
            title = "Title",
            description = "Desc",
        )
        val record = newRecordEntity.toLocal()
        coEvery { recordsDataSource.updateRecord(record) } returns newRecordEntity.id

        val result = recordsRepository.saveRecord(newRecordEntity)

        coVerify { recordsDataSource.updateRecord(record) }
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