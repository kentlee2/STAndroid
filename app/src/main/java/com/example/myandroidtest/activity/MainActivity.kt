package com.example.myandroidtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myandroidtest.R
import com.example.myandroidtest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        //将App bar与NavController绑定
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    override fun finish() {
        super.finish()
        //将弹出动画应用于 Activity 过渡
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}