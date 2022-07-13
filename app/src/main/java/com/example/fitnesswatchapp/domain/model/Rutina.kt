package com.example.fitnesswatchapp.domain.model

import com.example.fitnesswatchapp.data.database.entities.RutinaEntity

data class Rutina(
    var id_rutina: Int,
    var nombre: String,
    var sesiones: Int,
    var minutos_trabajo: Int,
    var segundos_trabajo: Int,
    var minutos_descanso: Int,
    var segundos_descanso: Int
)

// función de extensión convertimos un objeto RutinaEntity a obj Rutina
fun RutinaEntity.toDomain() =
    Rutina(
        id_rutina,
        nombre,
        sesiones,
        minutos_trabajo,
        segundos_trabajo,
        minutos_descanso,
        segundos_descanso
    )