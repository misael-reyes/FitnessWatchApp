package com.example.fitnesswatchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fitnesswatchapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    //preparamos el view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //parte del binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //esto es para el menú inferior
        val navView: BottomNavigationView = binding.navView

        //necesitamos un navigation controlle el cual es el fragment donde se alojaran
        //los demás fragment
        val navController = findNavController(R.id.navigation_host_fragment)

        //se pasan los id de los diferentes destinos
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_schedule, R.id.navigation_list
            )
        )
        //le damos acción a los botones del menú inferior
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}