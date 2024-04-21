package com.chanyoung.jack.data.source.pagination

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.data.source.pagination.basic.JBasePagingSource
import javax.inject.Singleton

@Singleton
class ListLinkInGroupPagingSource(
    private val linkRepo: LinkRepositoryImpl,
    private val groupId : Int
) : JBasePagingSource<Link>() {

    override suspend fun loadFromRepo(params: LoadParams<Int>): List<Link> {
        return linkRepo.getPaginatedLinksByGroupId(
            params.key ?: INIT_PAGE_INDEX,
            params.loadSize,
            groupId)
    }

}

