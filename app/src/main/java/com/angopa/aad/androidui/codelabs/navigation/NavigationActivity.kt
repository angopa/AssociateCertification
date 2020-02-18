package com.angopa.aad.androidui.codelabs.navigation

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityNavigationBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class NavigationActivity : BaseActivity() {
    private lateinit var binding: ActivityNavigationBinding

    private lateinit var navController: NavController

    override fun getScreenTitle(): Int = R.string.navigation_screen_title

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityNavigationBinding>(
            this,
            R.layout.activity_navigation
        ).apply {
            navController = findNavController(R.id.nav_host_navigation)

            val appBarConfiguration = AppBarConfiguration(setOf())

            toolbar.setupWithNavController(navController, appBarConfiguration)



            handleNavigationChanges()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun handleNavigationChanges() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_detail_page -> {
                    Toast.makeText(this@NavigationActivity, "Detail", Toast.LENGTH_SHORT).show()
                }
                R.id.fragment_navigation_dashboard -> {
                    Toast.makeText(this@NavigationActivity, "Dashboard", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}