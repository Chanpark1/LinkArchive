package com.chanyoung.jack.data.repository.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import javax.inject.Singleton

@Singleton
class ListLinkPagingSource(
    private val linkRepo: LinkRepositoryImpl
) : PagingSource<Int, Link>() {

    private companion object {
        const val INIT_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Link> {
        return try {
            val currentPage = params.key ?: INIT_PAGE_INDEX
            val pageSize = params.loadSize

            val links = linkRepo.getPaginatedLinks(currentPage, pageSize)

            LoadResult.Page(
                data = links,
                prevKey = if (currentPage == INIT_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (links.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Link>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}