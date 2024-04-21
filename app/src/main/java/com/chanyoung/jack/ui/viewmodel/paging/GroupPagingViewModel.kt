package com.chanyoung.jack.ui.viewmodel.paging

import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.source.pagination.ListGroupPagingSource
import com.chanyoung.jack.ui.viewmodel.paging.basic.JBasePagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupPagingViewModel @Inject constructor(
    pagingSource: ListGroupPagingSource
) : JBasePagingViewModel<LinkGroup>(pagingSource) {

    override fun refreshData() {
        loadData()
    }
}