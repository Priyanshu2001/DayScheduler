package com.prbansal.dayscheduler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController : NavController = findNavController(R.id.activity_main_nav_host_fragment)
        bottomNavView.setupWithNavController(navController)
    }
}




