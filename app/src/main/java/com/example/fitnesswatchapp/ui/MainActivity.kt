package com.example.fitnesswatchapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fitnesswatchapp.R
import com.example.fitnesswatchapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //preparamos el view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //parte del binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // si queremos poner el icono de nuestra app en el action bar
        // supportActionBar?.setDisplayShowHomeEnabled(true)
        // supportActionBar?.setIcon(R.mipmap.ic_clock_launcher)

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