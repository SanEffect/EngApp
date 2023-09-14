package com.san.englishbender.ui.records

import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.ifFailure
import com.san.englishbender.data.local.models.Record
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.ui.ViewModel
import dev.icerock.moko.resources.StringResource
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
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
    private val realm: Realm,
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

    fun showData() = safeLaunch {
        val records = realm.query<Record>().find()
        records.forEach { rec ->
            log(tag = "selectedTags") { "rec.id: ${rec.id}" }
            log(tag = "selectedTags") { "rec.title: ${rec.title}" }
            log(tag = "selectedTags") { "rec.tags:" }

            rec.tags.forEach { tag ->
                log(tag = "selectedTags") { "tag: $tag" }
            }
            log(tag = "selectedTags") { "----------------------" }
        }

        val tags = realm.query<Tag>().find()

        log(tag = "selectedTags") { "tags:" }
        tags.forEach { tag ->
            log(tag = "selectedTags") { "tag: ${tag.id}, ${tag.name}" }
            log(tag = "selectedTags") { "tag.records:" }
            tag.records.forEach { rec ->
                log(tag = "selectedTags") { "rec: ${rec.id}, ${rec.title}" }
            }
        }
        log(tag = "selectedTags") { "----------------------" }
    }

    fun removeRecord(record: RecordEntity) = safeLaunch {
        getResultFlow { removeRecordUseCase(record) }
            .ifFailure { RecordsUiState(userMessage = SharedRes.strings.remove_record_error) }
    }
}
