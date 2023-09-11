package com.san.englishbender.ui.records

import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.ifFailure
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordTagRefRepository
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.ui.ViewModel
import dev.icerock.moko.resources.StringResource
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class RecordsUiState(
    val isLoading: Boolean = false,
    val records: List<RecordEntity> = emptyList(),
    val labels: List<Tag> = emptyList(),
    val userMessage: StringResource? = null
)

class RecordsViewModel constructor(
    private val recordTagRefRepository: IRecordTagRefRepository,
    private val getRecordsUseCase: GetRecordsUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase
) : ViewModel() {

    val uiState: StateFlow<RecordsUiState> =
        getRecordsUseCase(forceUpdate = false)
            .map { RecordsUiState(records = it) }
            .catch { RecordsUiState(userMessage = SharedRes.strings.loading_records_error) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = RecordsUiState(isLoading = true)
            )

    fun showAllRecordTagRef() = safeLaunch {
        val refs = recordTagRefRepository.getAllRecordTagRef()
        refs.forEach {
            log(tag = "showAllRecordTagRef") { "recId:tagId: ${it.recordId}:${it.tagId}" }
        }
        log(tag = "showAllRecordTagRef") { "--------------------------" }
    }

//    fun saveRecords() = safeLaunch {
//        repeat(200) { i ->
//            val n = i + 79
//            val rec = RecordEntity(
//                id = "",
//                title = "Title $n",
//                description = "Description"
//            )
//            saveRecordUseCase(rec)
//        }
//    }
//
//    fun showAllRecords() = safeLaunch {
//
////        log(tag = "showAllRecords") { "showAllRecords" }
////
////        val tags = dataStoreRealm.getTags()
////        log(tag = "showAllRecords") { "tags.size: ${tags.size}" }
////        tags.forEach { tag ->
////            log(tag = "showAllRecords") { "name: ${tag.name}" }
//////            log(tag = "showAllRecords") { "record: ${tag.record}" }
//////            log(tag = "showAllRecords") { "records.size: ${tag.record.size}" }
//////
//////            tag.record.forEach { record ->
//////                log(tag = "showAllRecords") { "record.title: ${record.title}" }
//////            }
//////            log(tag = "showAllRecords") { "name: ${res.record}" }
////        }
//
////        log(tag = "showAllRecords") { "-------------------------" }
////        val tagRefs = dataStoreRealm.getRecordTagRefs()
////        tagRefs.forEach {
////            log(tag = "showAllRecords") { "tagRef.recId:tagId: ${it.recordId} : ${it.tagId}" }
////        }
////        log(tag = "showAllRecords") { "-------------------------" }
////
////        val recs = dataStoreRealm.getRecordsByTagId("1")
////        recs.forEach {
////            log(tag = "showAllRecords") { "recId:tagId: ${it.recordId}-${it.tagId}" }
////        }
//
//        val records = dataStoreRealm.getRecords()
//        log(tag = "showAllRecords") { "records.size: ${records.size}" }
//        records.forEach { record ->
//            val tagIds = record.tags?.map { it.tagId }
//            log(tag = "showAllRecords") { "Record: ${record.id} : ${record.title} : $tagIds" }
//        }
//    }
//
//    fun readRecordsTest() = safeLaunch {
//        measureTimeMillis("Read and map records") {
//            val records = dataStoreRealm.getRecords()
//            records.map { it.toEntity() }.forEach { record ->
//                log(tag = "showAllRecords") { "record.title: ${record.title}" }
//            }
//        }
//    }
//
//    fun getRecordsByTagId() = safeLaunch {
//        measureTimeMillis("Get records by tag") {
//            val randomTagId = dataStoreRealm.tags.random().id
//            val records = dataStoreRealm.getRecords()
//            val recordTagRefs = dataStoreRealm.getRecordsByTagId(randomTagId)
//            val recordsIds = recordTagRefs.map { it.recordId }
//            records.filter { recordsIds.contains(it.id) }.forEach {
//                log(tag = "showAllRecords") { "record.title: ${it.title}" }
//            }
//        }
//    }
//
//    fun sqlGetRecordsByTagIdTest() = safeLaunch {
//        measureTimeMillis("Get records by tag id") {
//            val randomTagId = recordsRepository.randomTagId.random()
//            val records = recordsRepository.getRecordsByTagIdTest(randomTagId)
//            records.forEach {
//                log(tag = "showAllRecords") { "record.title: ${it.title}" }
//            }
//        }
//    }

    fun removeRecord(record: RecordEntity) = safeLaunch {
        getResultFlow { removeRecordUseCase(record) }
            .ifFailure { RecordsUiState(userMessage = SharedRes.strings.remove_record_error) }
    }
}
