package com.chanyoung.jack.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.chanyoung.jack.R
import com.chanyoung.jack.databinding.ActivityMainBinding
import com.chanyoung.jack.ui.activity.base.JMainBasicActivity
import com.chanyoung.jack.ui.component.navigation.NavigationController
import com.chanyoung.jack.ui.viewmodel.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    JMainBasicActivity<ActivityMainBinding>() {

    private lateinit var navController: NavigationController

    private val groupViewModel: GroupViewModel by viewModels()

    override fun viewBindingInflate(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        groupViewModel.createDefaultLinkGroup()
        setBottomNav()
        setAddLinkButton()
    }

    private fun setBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = NavigationController(
            navHostFragment = navHostFragment,
            bottomNavigationView = binding.mainNav
        )
    }

    private fun setAddLinkButton() {
        binding.mainAddLinkBtn.setOnClickListener {
            val intent = Intent(this, AddLinkActivity::class.java)
            startActivity(intent)
        }
    }
}