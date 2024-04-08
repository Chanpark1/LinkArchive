package com.chanyoung.jack.ui.fragment.basic

import androidx.databinding.ViewDataBinding
import com.chanyoung.jmodule.fragment.DataBindingBasicFragment

abstract class JBasicFragment<T : ViewDataBinding> : DataBindingBasicFragment<T>() {

    protected open fun initRecyclerView(){}

    protected open fun setOnClickListener(){

    }
}