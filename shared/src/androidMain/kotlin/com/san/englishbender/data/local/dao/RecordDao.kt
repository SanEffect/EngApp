package com.san.englishbender.data.local.dao

//import androidx.paging.PagingSource
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import com.san.englishbender.data.local.models.RecordDto
//import database.RecordData
//
//@Dao
//interface RecordDao : BaseDao<RecordData> {
//    @Query("SELECT * FROM records WHERE is_deleted != 1 ORDER BY creation_date DESC")
//    fun getAllRecordsPaging() : PagingSource<Int, RecordDto>
//
//    @Query("SELECT * FROM records ORDER BY creation_date DESC LIMIT :pageSize OFFSET :offset")
//    suspend fun getRecordsPaging(pageSize: Int, offset: Int) : List<RecordDto>
//
//    @Query("SELECT * FROM records ORDER BY creation_date DESC")
//    suspend fun getRecords(): List<RecordDto>
//
//    @Query("SELECT * FROM records WHERE id = :key")
//    suspend fun get(key: String): RecordDto
//
//    @Query("SELECT * FROM records ORDER BY creation_date DESC LIMIT 1")
//    suspend fun getLastRecord(): RecordDto
//
//    @Query("DELETE FROM records WHERE id = :key")
//    suspend fun deleteRecord(key: String)
//
//    @Query("DELETE FROM records WHERE id IN (:ids)")
//    suspend fun deleteRecords(ids: List<String>)
//
//    @Query("UPDATE records SET is_deleted = 1 WHERE id = :key")
//    suspend fun removeRecord(key: String)
//
//    @Query("DELETE FROM records")
//    suspend fun clear()
//
//    @Query("SELECT count(*) FROM records")
//    suspend fun getRecordsCount(): Long
//
//    @Query("SELECT sum(length(trim(text))) + sum(length(trim(title))) FROM records")
//    suspend fun getLettersCount(): Long
//
//    @Query(
//        "SELECT CASE WHEN length(text) >= 1 " +
//                "THEN sum(length(title) - length(replace(title, ' ', '')) + 1) + sum(length(text) - length(replace(text, ' ', '')) + 1)" +
//                "ELSE sum(length(title) - length(replace(title, ' ', ''))) + sum(length(text) - length(replace(text, ' ', ''))) END as wordsCount " +
//                "FROM records"
//    )
////    @Query(
////        "SELECT CASE WHEN length(text) >= 1 " +
////                "THEN sum(length(text) - length(replace(text, ' ', '')) + 1) " +
////                "ELSE sum(length(text) - length(replace(text, ' ', ''))) END as wordsCount " +
////                "FROM records"
////    )
//    suspend fun getWordsCount(): Long
//
//    @Query("SELECT count(*) FROM records WHERE title LIKE :title AND text LIKE :text")
//    suspend fun check(title: String, text: String): Long
//
//    @Insert
//    fun insertRecordLabelCrossRef(crossRef: RecordLabelCrossRef)
//
//    @Query("SELECT * FROM records WHERE id IN (SELECT recordId FROM records_labels WHERE labelId IN (:labelIds))")
//    fun getRecordsByTags(labelIds: List<String>): List<RecordDto>
//}