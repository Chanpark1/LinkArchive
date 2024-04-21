package com.chanyoung.jack.ui.viewmodel.paging.basic

import androidx.paging.PagingSource
import com.chanyoung.jack.application.LinkInGroupProvider
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.data.source.pagination.ListLinkInGroupPagingSource
import com.chanyoung.jack.data.source.pagination.ListLinkPagingSource
import com.chanyoung.jack.data.source.pagination.ListLinkSearchPagingSource
import com.chanyoung.jack.data.source.pagination.ListGroupPagingSource


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

class ListLinkSearchPagingSourceParamsProvider(
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
        return ListLinkSearchPagingSource(linkRepo)
    }
}