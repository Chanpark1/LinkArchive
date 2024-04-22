package com.chanyoung.jack.data.source.pagination

import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.source.pagination.basic.JBasePagingSource

class ListGroupPagingSource(
    private val groupRepo: GroupRepositoryImpl
) : JBasePagingSource<LinkGroup>() {
    override suspend fun loadFromRepo(params: LoadParams<Int>): List<LinkGroup> {
        return groupRepo.getPaginatedGroups(params.key ?: INIT_PAGE_INDEX, params.loadSize)
    }


}