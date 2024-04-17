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
class LinkViewModel @Inject constructor(
    private val linkRepo: LinkRepositoryImpl
) : ViewModel() {

    private val _link = MutableLiveData<Link>()
    val link: LiveData<Link> get() = _link

    fun getLink(lid: Int) {
        viewModelScope.launch {
            val link = linkRepo.getLink(lid)
            _link.value = link
        }
    }

    fun deleteLink(lid: Int) {
        viewModelScope.launch {
            linkRepo.deleteLink(lid)
        }
    }

    fun insertLink(link: Link) {
        viewModelScope.launch {
            linkRepo.insertLink(link)
        }
    }

    fun relocateLink(lid: Int, gid: Int) {
        viewModelScope.launch {
            linkRepo.relocateLink(lid, gid)
        }
    }

    fun editLink(lid: Int, title: String, url: String, memo: String, image_path: String?) {
        viewModelScope.launch {
            linkRepo.updateLink(
                lid, title, url, memo, image_path
            )
        }
    }

}

