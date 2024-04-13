package com.chanyoung.jack.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkDetailViewModel @Inject constructor(
    private val linkRepo: LinkRepositoryImpl
) : ViewModel() {

    private val _link = MutableLiveData<Link>()
    val link: LiveData<Link> get() = _link

    fun getLinkInfo(lid: Int) {
        viewModelScope.launch {
            val link = linkRepo.getLink(lid)
            _link.value = link
        }
    }

}