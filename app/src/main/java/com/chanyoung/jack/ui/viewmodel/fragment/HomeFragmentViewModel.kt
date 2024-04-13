package com.chanyoung.jack.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.pagination.ListLinkPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val pagingSource: ListLinkPagingSource
) : ViewModel() {
    private companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_LOAD_SIZE = 5
        private const val PREFETCH_DISTANCE = 3
        private const val ENABLE_PLACEHOLDERS = false
    }

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        prefetchDistance = PREFETCH_DISTANCE,
        enablePlaceholders = ENABLE_PLACEHOLDERS
    )

    private val pager = Pager(pagingConfig) { pagingSource }

    val links : LiveData<PagingData<Link>> = pager.flow.map { PagingData -> PagingData }.asLiveData(viewModelScope.coroutineContext)

}