package com.chanyoung.jack.data.source.pagination.basic

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class JBasePagingSource<T : Any> : PagingSource<Int, T>() {

    protected companion object {
        const val INIT_PAGE_INDEX = 0
        const val DEFAULT_GROUP_ID = 0
    }

    abstract suspend fun loadFromRepo(params: LoadParams<Int>): List<T>


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: INIT_PAGE_INDEX
            val data = loadFromRepo(params)
            val prevKey = if (currentPage == INIT_PAGE_INDEX) null else currentPage - 1
            val nextKey = if (data.isEmpty()) null else currentPage + 1
            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}