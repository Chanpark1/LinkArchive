package com.chanyoung.jack.ui.component.navigation

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.chanyoung.jack.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationController(
    private val navHostFragment : NavHostFragment,
    private val bottomNavigationView: BottomNavigationView
) {

    private lateinit var navController: NavController

    init {
        initializeNavController()
    }

    private fun initializeNavController() {
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> bottomNavigationView.menu.findItem(R.id.homeFragment)?.isChecked =
                    true
                R.id.libraryFragment -> bottomNavigationView.menu.findItem(R.id.libraryFragment)?.isChecked =
                    true
                R.id.searchFragment -> bottomNavigationView.menu.findItem(R.id.searchFragment)?.isChecked =
                    true
                R.id.bookmarkFragment -> bottomNavigationView.menu.findItem(R.id.bookmarkFragment)?.isChecked =
                    true
                R.id.addLinkFragment -> bottomNavigationView.menu.findItem(R.id.homeFragment)?.isChecked =
                    true
                else -> {
                    // Uncheck all menu items if the destination doesn't match
                    bottomNavigationView.menu.setGroupCheckable(0, true, false)
                    bottomNavigationView.menu.setGroupCheckable(0, false, true)
                }
            }

        }

    }
}