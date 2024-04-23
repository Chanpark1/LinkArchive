package com.chanyoung.jmodule.base.activity

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/*
여기선 MainActivity의 기본 기능을 정의.
BottomNavigationView를 이용한 화면 전환을 관리하게 됨.
자식 클래스에서 getBottomNavigationHandlerMap()과 getBottomNavigation() 메서드를 구현하여 하단 네비게이션 메뉴 아이템과 해당 동작을 매핑
*/
abstract class JmMainBaseActivity<VB : ViewBinding> : JmBaseActivity<VB>() {

    abstract fun getViewBinding() : VB

}