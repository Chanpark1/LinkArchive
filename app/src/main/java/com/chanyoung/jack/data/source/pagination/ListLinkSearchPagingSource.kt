package com.chanyoung.jack.data.source.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import javax.inject.Singleton

@Singleton
class ListLinkSearchPagingSource(
    private val linkRepo: LinkRepositoryImpl
) : PagingSource<Int, Link>() {

    private companion object {
        private const val INIT_PAGE_INDEX = 0
    }
    override fun getRefreshKey(state: PagingState<Int, Link>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Link> {
        TODO("Not yet implemented")
    }


}