package com.example.fitnesswatchapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesswatchapp.data.database.entities.RutinaEntity

@Dao
interface RutinaDao {

    @Query("SELECT * FROM rutinas")
    suspend fun getAllRutinas():List<RutinaEntity>

    @Insert
    suspend fun insertRutina(rutinaEntity: RutinaEntity)

    @Delete
    suspend fun deleteRutina(rutinaEntity: RutinaEntity)

    @Query("DELETE FROM rutinas")
    suspend fun deleteAllRutinas()

}