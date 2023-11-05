package com.example.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    lateinit var nav_controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_controller = findNavController(R.id.fragmentContainerView)

    }

    override fun onNavigateUp(): Boolean {
        return nav_controller.navigateUp() || super.onNavigateUp()
    }
}