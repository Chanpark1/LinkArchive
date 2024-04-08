package com.chanyoung.jack.ui.viewmodel.fragment

import androidx.lifecycle.ViewModel
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val linkRepository: LinkRepositoryImpl,
    private val groupRepository: GroupRepositoryImpl
) : ViewModel() {


}