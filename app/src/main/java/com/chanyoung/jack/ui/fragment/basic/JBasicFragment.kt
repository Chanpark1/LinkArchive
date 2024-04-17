package com.chanyoung.jack.ui.fragment.basic

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.chanyoung.jack.ui.viewmodel.fragment.LinkPagingViewModel
import com.chanyoung.jmodule.fragment.DataBindingBasicFragment

abstract class JBasicFragment<T : ViewDataBinding> : DataBindingBasicFragment<T>() {


    protected open fun initRecyclerView(){}
    protected open fun setOnClickListener(){}
}