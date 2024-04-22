package com.chanyoung.jack.data.source.pagination

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.data.source.pagination.basic.JBasePagingSource

class ListLinkPagingSource(
    private val linkRepo: LinkRepositoryImpl
) : JBasePagingSource<Link>() {

    override suspend fun loadFromRepo(params: LoadParams<Int>): List<Link> {
        return linkRepo.getPaginatedLinks(params.key ?: INIT_PAGE_INDEX, params.loadSize)
    }
}