package com.san.englishbender.data.local

//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.san.englishbender.data.local.dao.RecordDao
//import com.san.englishbender.data.local.models.RecordDto
//
//
//class RecordsPagingSource(private val recordDao: RecordDao) : PagingSource<Int, RecordDto>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecordDto> {
//        try {
//            val page = params.key ?: 1
//            val pageSize = params.loadSize
//            val offset = (page - 1) * pageSize
//
//            val records = recordDao.getRecordsPaging(pageSize, offset)
//
//            val prevKey = if (page > 1) page - 1 else null
//            val nextKey = if (records.isNotEmpty()) page + 1 else null
//
//            return LoadResult.Page(
//                data = records,
//                prevKey = prevKey,
//                nextKey = nextKey
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, RecordDto>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}