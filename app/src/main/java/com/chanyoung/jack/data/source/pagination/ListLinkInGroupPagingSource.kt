package com.chanyoung.jack.data.source.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import javax.inject.Singleton

@Singleton
class ListLinkInGroupPagingSource(
    private val linkRepo: LinkRepositoryImpl,
    private val groupId: Int
) : PagingSource<Int, Link>() {

    private companion object {
        private const val INIT_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Link> {
        return try {
            val currentPage = params.key ?: INIT_PAGE_INDEX
            val loadSize = params.loadSize
            val data = linkRepo.getPaginatedLinksByGroupId(currentPage, loadSize, groupId)

            LoadResult.Page(
                data = data,
                prevKey = if(currentPage == INIT_PAGE_INDEX) null else currentPage -1,
                nextKey = if(data.isEmpty()) null else currentPage + 1
            )

        } catch (e : Exception) {
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

