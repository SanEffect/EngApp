package com.san.englishbender.domain.usecases

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.san.englishbender.data.Result
import com.san.englishbender.data.repositories.IRecordsRepository
import com.san.englishbender.domain.entities.Record
import java.io.IOException

class RecordsPagingSource(
    private val recordsRepository: IRecordsRepository
) : PagingSource<Int, Record>() {
    override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        val page = params.key ?: 1
        return try {
            var recordList = listOf<Record>()

            when (val result = recordsRepository.getRecords(false)) {
                is Result.Success -> {
                    recordList = result.data
                }
                is Result.Failure -> {}
            }

//            val recordList = response.res
//            val episodeList = response.results.orEmpty().toEpisodeDtoList()
//
//            episodeList.map {
//                val episodeFav = recordsRepository.getFavorite(it.id.orZero())
//                it.isFavorite = episodeFav != null
//            }

            LoadResult.Page(
                data = recordList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (recordList.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
}