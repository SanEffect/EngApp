package com.san.englishbender.data

import androidx.annotation.VisibleForTesting
import com.san.englishbender.data.dataSources.RecordsDataSourceTest
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.mappers.toLocal
import com.san.englishbender.data.repositories.RecordsRepository
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.ui.samplesRecords
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class TestRecordRepository : IRecordsRepository {

    private var shouldThrowError = false

//    private val recordsFlow: MutableSharedFlow<List<RecordEntity>> =
//        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val _savedRecords = MutableStateFlow(LinkedHashMap<String, RecordEntity>())
    val savedRecords: StateFlow<LinkedHashMap<String, RecordEntity>> = _savedRecords.asStateFlow()

    private val observableRecords: Flow<List<RecordEntity>> = savedRecords.map {
        if (shouldThrowError) {
            throw Exception("Test exception")
        } else {
            it.values.toList()
        }
    }

    // Test dependencies
    private var localDataSource = RecordsDataSourceTest(samplesRecords.toLocal())

    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    // Class under test
    private lateinit var recordsRepository: RecordsRepository

    @Before
    fun createRepository() {
//        localDataSource = RecordsDataSourceTest(samplesRecords.toData())
        recordsRepository = RecordsRepository(
            recordsDataSource = localDataSource
        )
    }

    /**
     * A test-only API to allow controlling the list of topics from tests.
     */
//    fun sendRecords(topics: List<RecordEntity>) {
//        recordsFlow.tryEmit(topics)
//    }

    override suspend fun getRecordsFlow(forceUpdate: Boolean): Flow<List<RecordEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecords(forceUpdate: Boolean): List<RecordEntity> {
        return localDataSource.getRecords().toEntity()
    }

    override suspend fun insertRecord(record: RecordEntity): String {
        TODO("Not yet implemented")
    }

    override suspend fun saveRecord(record: RecordEntity): String {
        TODO("Not yet implemented")
    }

    override fun getRecordFlowById(id: String): Flow<RecordEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecordById(id: String, forceUpdate: Boolean): RecordEntity? {
        TODO("Not yet implemented")
    }

    override fun getRecordWithLabels(id: String): Flow<RecordEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun removeRecord(recordId: String) {
        localDataSource.deleteRecordById(recordId)
//        _savedRecords.update { records ->
//            val newTasks = LinkedHashMap<String, RecordEntity>(records)
//            newTasks.remove(recordId)
//            newTasks
//        }
    }

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override fun getRecordsCount(): Flow<Long> {
        TODO("Not yet implemented")
    }

//    override suspend fun deleteAllTasks() {
//        _savedTasks.update {
//            LinkedHashMap()
//        }
//    }

    @VisibleForTesting
    fun addRecords(records: List<RecordEntity>) {
        _savedRecords.update { oldRecords ->
            val newRecords = LinkedHashMap<String, RecordEntity>(oldRecords)
            records.forEach { newRecords[it.id] = it }
            newRecords
        }
    }
}