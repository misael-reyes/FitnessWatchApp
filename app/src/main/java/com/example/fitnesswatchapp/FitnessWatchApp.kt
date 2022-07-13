package com.example.fitnesswatchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * todas las apps que usan Hilt deben contener una clase application
 *
 * está clase será la primera en llamarse cuando se incie la app
 */

@HiltAndroidApp
class FitnessWatchApp: Application()