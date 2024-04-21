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

    private val _insertGroupResult = MutableLiveData<Result<Unit>>()
    val insertGroupResult: LiveData<Result<Unit>> get() = _insertGroupResult

    private val _deleteResult = MutableLiveData<Result<Unit>>()
    val deleteResult : LiveData<Result<Unit>> get() = _deleteResult

    private val _editResult = MutableLiveData<Result<Unit>>()
    val editResult: LiveData<Result<Unit>> get ()= _editResult

    private val _checkDuplicate = MutableLiveData<Result<Unit>>()
    val checkDuplicate : LiveData<Result<Unit>> get() = _checkDuplicate

    private val _linkGroups = MutableLiveData<List<LinkGroup>>()
    val linkGroups : LiveData<List<LinkGroup>> get() = _linkGroups

    private val _linkGroup = MutableLiveData<LinkGroup>()
    val linkGroup: LiveData<LinkGroup> get() = _linkGroup

    private val _groupName = MutableLiveData<String>()
    val groupName: LiveData<String> get()= _groupName


    fun deleteGroup(gid : Int) {
        viewModelScope.launch {
            try {
                val group = groupRepo.getLinkGroup(gid)

                val linksInGroup =  linkRepo.getLinksInGroup(gid)

                for (link in linksInGroup) {
                    linkRepo.deleteLink(link)
                }
                groupRepo.deleteGroup(group)
                _deleteResult.value = Result.success(Unit)
            } catch (e: Exception) {
                _deleteResult.value = Result.failure(Error("Delete Failed"))
            }

        }
    }
    fun onGroupItemSelected(groupId : Int) {
        _selectedGroupId.value = groupId
    }

    fun getGroup(gid : Int) {
        viewModelScope.launch {
            try {
                val group = groupRepo.getLinkGroup(gid)

                _linkGroup.value = group
            } catch (e: Exception) {
                _linkGroup.value = null
            }
        }
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

    fun setGroupName(name : String) {
        _groupName.value = name
    }

    fun checkDuplicateName(name : String) {
        viewModelScope.launch {
            if(groupRepo.checkDuplicateGroup(name) >= 1) {
                _checkDuplicate.value = Result.failure(Error("Duplicate Name"))
            } else {
                _checkDuplicate.value = Result.success(Unit)
            }
        }
    }

    fun editGroup(name : String, gid : Int) {
        viewModelScope.launch {
            try {
                groupRepo.updateGroup(name, gid)
                _editResult.value = Result.success(Unit)
            } catch (e: Exception) {
                _editResult.value = Result.failure(Error("Edit Error"))
            } finally {
                _editResult.value = Result.failure(Error("Edit Error"))
            }
        }
    }

    fun insertGroup(linkGroup : LinkGroup) {
        viewModelScope.launch {
            try {
                groupRepo.insertGroup(linkGroup)

                val updatedGroups = _linkGroups.value?.toMutableList() ?: mutableListOf()

                val gid = groupRepo.getGroupId(linkGroup.name)

                val finalGroup = LinkGroup(gid = gid, name = linkGroup.name)

                updatedGroups.add(finalGroup)

                _linkGroups.value = updatedGroups

                _insertGroupResult.value = Result.success(Unit)
            } catch (e : Exception) {
                _insertGroupResult.value = Result.failure(Error("Edit Error"))
            } finally {
                _insertGroupResult.value = Result.failure(Error("Edit Error"))
            }
        }
    }

    fun createDefaultLinkGroup() {
        viewModelScope.launch {
            groupRepo.createDefaultGroup()
        }
    }


}