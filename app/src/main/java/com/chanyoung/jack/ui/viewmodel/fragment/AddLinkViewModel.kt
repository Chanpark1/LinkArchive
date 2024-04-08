package com.chanyoung.jack.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLinkViewModel @Inject constructor(
    private val linkRepo : LinkRepositoryImpl,
    private val groupRepo : GroupRepositoryImpl
) : ViewModel() {

    private val _selectedGroupId = MutableLiveData<Int>()
    val selectedGroupId : LiveData<Int> get() = _selectedGroupId

    private val _linkGroups = MutableLiveData<List<LinkGroup>>()
    val linkGroups : LiveData<List<LinkGroup>> get() = _linkGroups

    fun onGroupItemSelected(groupId : Int) {
        _selectedGroupId.value = groupId
    }

    fun setGroupList() {
        viewModelScope.launch {
            val groups = groupRepo.getAllGroup()
            _linkGroups.value = groups
        }
    }

    fun insertGroup(linkGroup : LinkGroup) {
        viewModelScope.launch {
            groupRepo.insertGroup(linkGroup)
            val updatedGroups = _linkGroups.value?.toMutableList() ?: mutableListOf()
            updatedGroups.add(linkGroup)
            _linkGroups.value = updatedGroups
        }
    }

    fun insertLink(link: Link) {
        viewModelScope.launch {
            linkRepo.insertLink(link)
        }
    }


}

