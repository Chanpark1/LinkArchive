package com.chanyoung.jack.ui.viewmodel.paging

import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.ui.viewmodel.paging.basic.JBasePagingViewModel
import com.chanyoung.jack.data.source.pagination.basic.PagingSourceParamsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupPagingViewModel @Inject constructor(
    pagingSourceParamsProvider : PagingSourceParamsProvider<LinkGroup>
) : JBasePagingViewModel<LinkGroup>(pagingSourceParamsProvider) {

    override fun refreshData() {
        loadData()
    }
}