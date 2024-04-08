package com.chanyoung.jack.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.ActivityMainBinding
import com.chanyoung.jack.ui.activity.base.JMainBasicActivity
import com.chanyoung.jack.ui.component.navigation.NavigationController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    JMainBasicActivity<ActivityMainBinding>()
{

    private lateinit var navController: NavigationController

    override fun viewBindingInflate(inflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNav()
    }

    private fun setBottomNav() {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navController = NavigationController(
        navHostFragment = navHostFragment,
        bottomNavigationView = binding.mainNav
    )
    }

}