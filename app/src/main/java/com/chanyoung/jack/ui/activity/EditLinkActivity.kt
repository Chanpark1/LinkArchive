package com.chanyoung.jack.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.chanyoung.jack.databinding.ActivityEditLinkBinding
import com.chanyoung.jack.ui.activity.base.JMainBasicActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditLinkActivity : JMainBasicActivity<ActivityEditLinkBinding>() {
    override fun viewBindingInflate(inflater: LayoutInflater): ActivityEditLinkBinding = ActivityEditLinkBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun initRecyclerview() {

    }

    private fun getLinkId() : Int {
        return intent.getIntExtra("lid",0)
    }
}