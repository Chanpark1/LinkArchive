package com.chanyoung.jmodule.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

// 모든 액티비티의 기본 기능을 정의하는 추상 클래스. ViewBinding을 이용하여 XML 레이아웃을 액티비티와 매핑하고, 액티비티 생명주기 중에 실행될 메서드 정의.

abstract class JmBaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBindingInflate(layoutInflater)
        setContentView(binding.root)
    }

    // abstract methods
    /** 여기서 T.inflate(inflater) 호출할 것.  viewBindingInflate 상속을 강제하기 위함.*/
    abstract fun viewBindingInflate(inflater: LayoutInflater): VB
}
