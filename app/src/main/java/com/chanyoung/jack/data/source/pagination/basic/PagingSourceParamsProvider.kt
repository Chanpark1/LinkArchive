package com.chanyoung.jack.data.source.pagination.basic

import androidx.paging.PagingSource
import com.chanyoung.jack.application.LinkInGroupProvider
import com.chanyoung.jack.application.SearchInGroupProvider
import com.chanyoung.jack.application.SearchProvider
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.data.source.pagination.*


interface PagingSourceParamsProvider<T : Any> {
    fun getPagingSource(): PagingSource<Int, T>
}

class ListLinkPagingSourceParamsProvider(
    private val linkRepo: LinkRepositoryImpl
) : PagingSourceParamsProvider<Link> {
    override fun getPagingSource(): PagingSource<Int, Link> {
        return ListLinkPagingSource(linkRepo)
    }
}

class ListGroupPagingSourceParamsProvider(
    private val groupRepo : GroupRepositoryImpl
) : PagingSourceParamsProvider<LinkGroup> {
    override fun getPagingSource(): PagingSource<Int, LinkGroup> {
        return ListGroupPagingSource(groupRepo)
    }
}

class ListLinkInGroupPagingSourceParamsProvider(
    private val linkRepo: LinkRepositoryImpl
) : PagingSourceParamsProvider<Link> {
    private var groupId: Int = 0

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
    }

    @LinkInGroupProvider
    override fun getPagingSource(): PagingSource<Int, Link> {
        return ListLinkInGroupPagingSource(linkRepo, groupId)
    }
}
@SearchInGroupProvider
class ListLinkSearchInGroupPagingSourceParamsProvider(
    private val linkRepo: LinkRepositoryImpl
) : PagingSourceParamsProvider<Link> {

    private var groupId: Int = 0
    private var queryData : String = ""

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
    }

    fun setQueryData(query: String) {
        this.queryData = query
    }

    override fun getPagingSource(): PagingSource<Int, Link> {
        return ListLinkSearchInGroupPagingSource(linkRepo, groupId, queryData)
    }
}
@SearchProvider
class ListLinkSearchPagingSourceParamsProvider(
    private val linkRepo: LinkRepositoryImpl
) : PagingSourceParamsProvider<Link> {

    private var queryData : String = ""
    fun setQueryData(query: String) {
        this.queryData = query
    }
    override fun getPagingSource(): PagingSource<Int, Link> {
        return ListLinkSearchPagingSource(linkRepo,queryData)
    }
}