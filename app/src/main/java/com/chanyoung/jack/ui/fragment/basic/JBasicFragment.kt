package com.chanyoung.jack.ui.fragment.basic

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chanyoung.jmodule.fragment.DataBindingBasicFragment

abstract class JBasicFragment<T : ViewDataBinding> : DataBindingBasicFragment<T>() {

    protected open fun initRecyclerView(){}

    protected open fun setOnClickListener(){

    }
}