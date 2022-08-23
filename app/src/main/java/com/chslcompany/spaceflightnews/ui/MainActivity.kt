package com.chslcompany.spaceflightnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.chslcompany.spaceflightnews.R
import com.chslcompany.spaceflightnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.string.latest_news, R.string.latest_blogs, R.string.latest_reports
            )
        )
        binding.mainToolbar.setupWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }
}