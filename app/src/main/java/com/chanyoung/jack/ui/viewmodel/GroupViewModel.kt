package com.chanyoung.jack.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val linkRepo : LinkRepositoryImpl,
    private val groupRepo : GroupRepositoryImpl
) : ViewModel() {

    private val _selectedGroupId = MutableLiveData<Int>()
    val selectedGroupId : LiveData<Int> get() = _selectedGroupId

    private val _insertGroupResult = MutableLiveData<Boolean>()
    val insertGroupResult: LiveData<Boolean> get() = _insertGroupResult

    private val _linkGroups = MutableLiveData<List<LinkGroup>>()
    val linkGroups : LiveData<List<LinkGroup>> get() = _linkGroups

    fun deleteGroup(gid : Int) {
        viewModelScope.launch {
            val group = groupRepo.getLinkGroup(gid)

            val linksInGroup =  linkRepo.getLinksInGroup(gid)

            for (link in linksInGroup) {
                linkRepo.deleteLink(link)
            }
            groupRepo.deleteGroup(group)
        }
    }

    fun onGroupItemSelected(groupId : Int) {
        _selectedGroupId.value = groupId
    }

    fun setGroupListExcept(lid : Int) {
        viewModelScope.launch {
            val link = linkRepo.getLink(lid)
            val groups = groupRepo.getAllGroupExcept(link.groupId)
            _linkGroups.value = groups
        }
    }

    fun setGroupList() {
        viewModelScope.launch {
            val groups = groupRepo.getAllGroup()
            _linkGroups.value = groups
        }
    }

    fun insertGroup(linkGroup : LinkGroup) {
        viewModelScope.launch {
            if(groupRepo.checkDuplicateGroup(linkGroup.name) < 1) {

                _insertGroupResult.value = true

                groupRepo.insertGroup(linkGroup)

                val updatedGroups = _linkGroups.value?.toMutableList() ?: mutableListOf()

                val gid = groupRepo.getGroupId(linkGroup.name)

                val finalGroup = LinkGroup(gid = gid, name = linkGroup.name)

                updatedGroups.add(finalGroup)

                _linkGroups.value = updatedGroups

            } else {
                _insertGroupResult.value = false
            }

        }
    }

    fun createDefaultLinkGroup() {
        viewModelScope.launch {
            groupRepo.createDefaultGroup()
        }
    }


}