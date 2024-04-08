package com.chanyoung.jack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val linkRepository: LinkRepositoryImpl,
    private val groupRepository: GroupRepositoryImpl
) : ViewModel() {
    // 생성된 LinkGroup 이 없다면 이를 확인 하고 DefaultGroup 을 생성 해주는 메서드
     fun createDefaultLinkGroup() {
        viewModelScope.launch {
            groupRepository.createDefaultGroup()
        }
    }
}