package com.chanyoung.jack.ui.viewmodel.paging

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.source.pagination.ListLinkPagingSource
import com.chanyoung.jack.ui.viewmodel.paging.basic.JBasePagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkPagingViewModel @Inject constructor(
     pagingSource: ListLinkPagingSource
) : JBasePagingViewModel<Link>(pagingSource) {

    override fun refreshData() {
        loadData()
    }

}
