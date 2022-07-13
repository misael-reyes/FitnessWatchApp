package com.example.fitnesswatchapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnesswatchapp.domain.model.Rutina

@Entity(tableName = "rutinas")
data class RutinaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_rutina") val id_rutina: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "sesiones") val sesiones: Int,
    @ColumnInfo(name = "minutos_trabajo") val minutos_trabajo: Int,
    @ColumnInfo(name = "segundos_trabajo") val segundos_trabajo: Int,
    @ColumnInfo(name = "minutos_descanso") val minutos_descanso: Int,
    @ColumnInfo(name = "segundos_descanso") val segundos_descanso: Int
)

/**
 * convertimos un objeto Rutina a un objeto RutinaEntity
 */
fun Rutina.toDatabase() = RutinaEntity(
    id_rutina = id_rutina,
    nombre = nombre,
    sesiones = sesiones,
    minutos_trabajo = minutos_trabajo,
    segundos_trabajo = segundos_trabajo,
    minutos_descanso = minutos_descanso,
    segundos_descanso = segundos_descanso
)