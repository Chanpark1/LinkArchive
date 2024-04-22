package com.chanyoung.jack.ui.viewmodel.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chanyoung.jack.application.SearchInGroupProvider
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.source.pagination.basic.ListLinkSearchInGroupPagingSourceParamsProvider
import com.chanyoung.jack.ui.viewmodel.paging.basic.JBasePagingViewModel

import com.chanyoung.jack.data.source.pagination.basic.PagingSourceParamsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkSearchInGroupPagingViewModel @Inject constructor(
    @SearchInGroupProvider pagingSourceParamsProvider: PagingSourceParamsProvider<Link>
) : JBasePagingViewModel<Link>(pagingSourceParamsProvider) {

    private val _groupId = MutableLiveData<Int>()
    val groupId: LiveData<Int> get() = _groupId

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    fun initGroupId(gid : Int) {
        this._groupId.value = gid

        if(pagingSourceParamsProvider is ListLinkSearchInGroupPagingSourceParamsProvider) {
            pagingSourceParamsProvider.setGroupId(gid)
        }
    }

    fun updateQueryData(query : String) {
        this._query.value = query

        if(pagingSourceParamsProvider is ListLinkSearchInGroupPagingSourceParamsProvider) {
            pagingSourceParamsProvider.setQueryData(query)

            loadData()
        }
    }
}