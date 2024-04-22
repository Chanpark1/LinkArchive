package com.chanyoung.jack.ui.viewmodel.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chanyoung.jack.application.SearchProvider
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.ui.viewmodel.paging.basic.JBasePagingViewModel
import com.chanyoung.jack.ui.viewmodel.paging.basic.ListLinkSearchPagingSourceParamsProvider
import com.chanyoung.jack.ui.viewmodel.paging.basic.PagingSourceParamsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkSearchPagingViewModel @Inject constructor(
    @SearchProvider pagingSourceParamsProvider: PagingSourceParamsProvider<Link>
) : JBasePagingViewModel<Link>(pagingSourceParamsProvider) {

    private val _groupId = MutableLiveData<Int>()
    val groupId: LiveData<Int> get() = _groupId

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    fun initGroupId(gid : Int) {
        this._groupId.value = gid

        if(pagingSourceParamsProvider is ListLinkSearchPagingSourceParamsProvider) {
            pagingSourceParamsProvider.setGroupId(gid)
        }
    }

    fun updateQueryData(query : String) {
        this._query.value = query

        if(pagingSourceParamsProvider is ListLinkSearchPagingSourceParamsProvider) {
            pagingSourceParamsProvider.setQueryData(query)

            loadData()
        }
    }
}