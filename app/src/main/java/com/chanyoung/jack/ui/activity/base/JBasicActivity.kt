package com.chanyoung.jack.ui.activity.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.chanyoung.jack.R
import com.chanyoung.jmodule.activity.JmBaseActivity

abstract class JBasicActivity<T : ViewBinding> : JmBaseActivity<T>() {

    protected abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate()
    }

}