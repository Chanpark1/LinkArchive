package com.chanyoung.jack.ui.viewmodel.fragment

import androidx.lifecycle.*
import androidx.paging.*
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.pagination.ListLinkPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val pagingSource: ListLinkPagingSource
) : ViewModel() {
    private companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_LOAD_SIZE = 5
        private const val PREFETCH_DISTANCE = 3
        private const val ENABLE_PLACE_HOLDERS = false
    }

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        prefetchDistance = PREFETCH_DISTANCE,
        enablePlaceholders = ENABLE_PLACE_HOLDERS
    )

    private val _links = MutableLiveData<PagingData<Link>>()
    val links: LiveData<PagingData<Link>> get() = _links

    init {
        loadData()
    }

    private fun loadData() {
        val pager = Pager(pagingConfig) { pagingSource }

        pager.flow
            .cachedIn(viewModelScope)
            .asLiveData()
            .observeForever { pagingData ->
                _links.value = pagingData
            }

    }

    fun refreshData() {
        loadData()
    }

}
