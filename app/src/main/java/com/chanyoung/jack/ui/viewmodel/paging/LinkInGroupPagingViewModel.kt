package com.chanyoung.jack.ui.viewmodel.paging

import androidx.lifecycle.*
import com.chanyoung.jack.application.LinkInGroupProvider
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.ui.viewmodel.paging.basic.JBasePagingViewModel
import com.chanyoung.jack.data.source.pagination.basic.ListLinkInGroupPagingSourceParamsProvider
import com.chanyoung.jack.data.source.pagination.basic.PagingSourceParamsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkInGroupPagingViewModel @Inject constructor(
   @LinkInGroupProvider pagingSourceParamsProvider: PagingSourceParamsProvider<Link>
): JBasePagingViewModel<Link>(pagingSourceParamsProvider) {

    private val _groupId = MutableLiveData<Int>()
    val groupId: LiveData<Int> get() = _groupId

    override fun refreshData() {
        loadData()
    }

    fun initGroupId(gid : Int) {
        this._groupId.value = gid

        if (pagingSourceParamsProvider is ListLinkInGroupPagingSourceParamsProvider) {
            pagingSourceParamsProvider.setGroupId(gid)
        }

    }

}