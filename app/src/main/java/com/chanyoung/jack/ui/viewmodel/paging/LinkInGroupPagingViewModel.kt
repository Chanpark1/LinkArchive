package com.chanyoung.jack.ui.viewmodel.paging

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.data.source.pagination.ListLinkInGroupPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkInGroupPagingViewModel @Inject constructor(
    private val linkRepo : LinkRepositoryImpl
) : ViewModel() {

    private var gid : Int = 0

    private companion object {
        private const val PAGE_SIZE = 15
        private const val INITIAL_LOAD_SIZE = PAGE_SIZE * 3
        private const val ENABLE_PLACE_HOLDERS = false
    }

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        enablePlaceholders = ENABLE_PLACE_HOLDERS
    )


    private val _items = MutableLiveData<PagingData<Link>>()
    val items: LiveData<PagingData<Link>> get() = _items

    private val _groupId = MutableLiveData<Int>()
    val groupId: LiveData<Int> get() = _groupId


    private fun loadData() {
        val pagingSource = ListLinkInGroupPagingSource(linkRepo, gid)

        val pager = Pager(pagingConfig) {pagingSource}

        pager.flow
            .cachedIn(viewModelScope)
            .asLiveData()
            .observeForever { pagingData ->
                _items.value = pagingData
            }
    }

    fun refreshData() {
        loadData()
    }

    fun initGroupId(gid : Int) {
        this.gid = gid
        _groupId.value = gid
    }

}