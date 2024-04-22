package com.chanyoung.jack.ui.viewmodel.paging.basic

import androidx.lifecycle.*
import androidx.paging.*
import com.chanyoung.jack.data.source.pagination.basic.PagingSourceParamsProvider

abstract class JBasePagingViewModel<T : Any>(
    val pagingSourceParamsProvider: PagingSourceParamsProvider<T>
) : ViewModel() {

    protected companion object {
        private const val PAGE_SIZE = 15
        private const val INITIAL_LOAD_SIZE = PAGE_SIZE * 3
        private const val ENABLE_PLACE_HOLDERS = false
    }


    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        enablePlaceholders = ENABLE_PLACE_HOLDERS
    )

    private val _items = MutableLiveData<PagingData<T>>()
    val items: LiveData<PagingData<T>> get() = _items

    protected fun loadData() {
        val pager = Pager(pagingConfig) { pagingSourceParamsProvider.getPagingSource() }
        pager.flow
            .cachedIn(viewModelScope)
            .asLiveData()
            .observeForever { pagingData ->
                _items.value = pagingData
            }

    }

    open fun refreshData() {
        loadData()
    }
}