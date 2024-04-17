package com.chanyoung.jack.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanyoung.jack.application.WebUrlUtil
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.ui.component.clipboard.JClipboardManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkViewModel @Inject constructor(
    private val linkRepo : LinkRepositoryImpl,
    private val groupRepo : GroupRepositoryImpl
) : ViewModel() {


    private val _selectedGroupId = MutableLiveData<Int>()
    val selectedGroupId : LiveData<Int> get() = _selectedGroupId

    private val _linkGroups = MutableLiveData<List<LinkGroup>>()
    val linkGroups : LiveData<List<LinkGroup>> get() = _linkGroups

    private val _link = MutableLiveData<Link>()
    val link: LiveData<Link> get() = _link

    private val _insertGroupResult = MutableLiveData<Boolean>()
    val insertGroupResult: LiveData<Boolean> get() = _insertGroupResult

    private val _clipData = MutableLiveData<String>()
    val clipData : LiveData<String> get() = _clipData

    fun onGroupItemSelected(groupId : Int) { _selectedGroupId.value = groupId }

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

    fun getLink(lid : Int) {
        viewModelScope.launch {
            val link = linkRepo.getLink(lid)
            _link.value = link
        }
    }

    fun deleteLink(lid : Int) {
        viewModelScope.launch {
            linkRepo.deleteLink(lid)
        }
    }

    fun insertLink(link: Link) {
        viewModelScope.launch {
            linkRepo.insertLink(link)
        }
    }

    fun relocateLink(lid : Int, gid : Int) {
        viewModelScope.launch {
            linkRepo.relocateLink(lid, gid)
        }
    }


   fun handlerPrimaryClip(context : Context, delayMillis: Long) {

       viewModelScope.launch {
           delay(delayMillis)

           val clipboardManager = JClipboardManager.getClipboardManager(context)
           if(clipboardManager.hasPrimaryClip()) {
               try {
                   val clip = clipboardManager.primaryClip
                   val text = clip?.getItemAt(0)?.text?.toString()
                   if (text != null && WebUrlUtil.checkUrlPrefix(text)) {
                       _clipData.value = text
                   }
               } catch (e : Exception) {
                   e.printStackTrace()
                   _clipData.value = null
               }

           }

       }
    }


}

