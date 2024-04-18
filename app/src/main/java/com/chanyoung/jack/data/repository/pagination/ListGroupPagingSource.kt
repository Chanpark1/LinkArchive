package com.chanyoung.jack.data.repository.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import javax.inject.Singleton

@Singleton
class ListGroupPagingSource(
    private val groupRepo: GroupRepositoryImpl
) : PagingSource<Int, LinkGroup>() {

    private companion object {
        const val INIT_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LinkGroup> {
        return try {
            val currentPage = params.key ?: INIT_PAGE_INDEX
            val pageSize = params.loadSize

            val groups = groupRepo.getPaginatedGroups(currentPage, pageSize)

            LoadResult.Page(
                data = groups,
                prevKey = if (currentPage == INIT_PAGE_INDEX) null else currentPage -1,
                nextKey = if (groups.isEmpty()) null else currentPage + 1
            )
        } catch (e:Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LinkGroup>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}