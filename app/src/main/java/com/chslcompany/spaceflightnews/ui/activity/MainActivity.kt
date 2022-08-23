package com.chslcompany.spaceflightnews.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chslcompany.spaceflightnews.R
import com.chslcompany.spaceflightnews.databinding.ActivityMainBinding
import android.view.MenuItem




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_news -> {
                    supportActionBar?.title = getString(R.string.latest_news)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.navigation_blogs -> {
                    supportActionBar?.title = getString(R.string.latest_blogs)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.navigation_report -> {
                    supportActionBar?.title = getString(R.string.latest_reports)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else -> {
                    supportActionBar?.title = getString(R.string.details)
                    supportActionBar?.setDisplayShowHomeEnabled(true)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}