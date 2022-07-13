package com.example.fitnesswatchapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnesswatchapp.data.database.dao.RutinaDao
import com.example.fitnesswatchapp.data.database.entities.RutinaEntity

/**
 * clase para crear la base de datos local
 */

@Database(
    entities = [RutinaEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    // función abstracta por cada dao
    abstract fun rutinaDao(): RutinaDao


}
