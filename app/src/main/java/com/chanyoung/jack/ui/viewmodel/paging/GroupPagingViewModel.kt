package com.chanyoung.jack.ui.viewmodel.paging

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.pagination.ListGroupPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupPagingViewModel @Inject constructor(
    private val pagingSource: ListGroupPagingSource
) : ViewModel() {

    private companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_LOAD_SIZE = PAGE_SIZE * 3
        private const val PREFETCH_DISTANCE = 3
        private const val ENABLE_PLACE_HOLDERS = false
    }

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        prefetchDistance = PREFETCH_DISTANCE,
        enablePlaceholders = ENABLE_PLACE_HOLDERS
    )

    private val _groups = MutableLiveData<PagingData<LinkGroup>>()
    val groups: LiveData<PagingData<LinkGroup>> get() = _groups

    private fun loadData() {

        val pager = Pager(pagingConfig) {pagingSource}

        pager.flow
            .cachedIn(viewModelScope)
            .asLiveData()
            .observeForever { pagingData ->
                _groups.value = pagingData
            }
    }

    fun refreshData() {
        loadData()
    }


}