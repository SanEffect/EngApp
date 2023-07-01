package com.san.englishbender.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.paging.PagingSource
import com.san.englishbender.data.local.dto.RecordDto
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordsDao : BaseDao<RecordDto> {
    @Query("SELECT * FROM records WHERE is_deleted != 1 ORDER BY creation_date DESC")
    fun getRecordsPaging() : PagingSource<Int, RecordDto>

//    @Query("SELECT * FROM records WHERE is_deleted != 1 ORDER BY creation_date DESC")
//    fun getRecordsPaging() : DataSource.Factory<Int, RecordDto>

    @Query("SELECT * FROM records ORDER BY creation_date DESC")
    suspend fun getRecords(): List<RecordDto>

    @Query("SELECT * FROM records WHERE id = :key")
    suspend fun get(key: String): RecordDto

    @Query("SELECT * FROM records ORDER BY creation_date DESC LIMIT 1")
    suspend fun getLastRecord(): RecordDto

    @Query("DELETE FROM records WHERE id = :key")
    suspend fun deleteRecord(key: String)

    @Query("DELETE FROM records WHERE id IN (:ids)")
    suspend fun deleteRecords(ids: List<String>)

    @Query("UPDATE records SET is_deleted = 1 WHERE id = :key")
    suspend fun removeRecord(key: String)

    @Query("DELETE FROM records")
    suspend fun clear()

    @Query("SELECT count(*) FROM records")
    suspend fun getRecordsCount(): Long

    @Query("SELECT sum(length(trim(text))) + sum(length(trim(title))) FROM records")
    suspend fun getLettersCount(): Long

    @Query(
        "SELECT CASE WHEN length(text) >= 1 " +
                "THEN sum(length(title) - length(replace(title, ' ', '')) + 1) + sum(length(text) - length(replace(text, ' ', '')) + 1)" +
                "ELSE sum(length(title) - length(replace(title, ' ', ''))) + sum(length(text) - length(replace(text, ' ', ''))) END as wordsCount " +
                "FROM records"
    )
//    @Query(
//        "SELECT CASE WHEN length(text) >= 1 " +
//                "THEN sum(length(text) - length(replace(text, ' ', '')) + 1) " +
//                "ELSE sum(length(text) - length(replace(text, ' ', ''))) END as wordsCount " +
//                "FROM records"
//    )
    suspend fun getWordsCount(): Long

    @Query("SELECT count(*) FROM records WHERE title LIKE :title AND text LIKE :text")
    suspend fun check(title: String, text: String): Long
}