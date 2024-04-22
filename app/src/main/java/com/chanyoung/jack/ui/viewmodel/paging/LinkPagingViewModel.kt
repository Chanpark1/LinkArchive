package com.chanyoung.jack.ui.viewmodel.paging

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.ui.viewmodel.paging.basic.JBasePagingViewModel
import com.chanyoung.jack.data.source.pagination.basic.PagingSourceParamsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkPagingViewModel @Inject constructor(
    pagingSourceParamsProvider : PagingSourceParamsProvider<Link>
) : JBasePagingViewModel<Link>(pagingSourceParamsProvider) {

    override fun refreshData() {
        loadData()
    }

}
