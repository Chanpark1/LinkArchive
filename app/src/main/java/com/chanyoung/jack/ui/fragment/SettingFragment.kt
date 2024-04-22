package com.chanyoung.jack.ui.fragment

import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.FragmentSettingBinding
import com.chanyoung.jack.ui.fragment.basic.JBasicFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class SettingFragment  @Inject constructor() :
    JBasicFragment<FragmentSettingBinding>() {
    override fun onCreateView() {
    }

    override fun layoutId(): Int = R.layout.fragment_setting

}