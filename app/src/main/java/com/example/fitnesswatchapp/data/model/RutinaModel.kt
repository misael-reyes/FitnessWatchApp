package com.example.fitnesswatchapp.data.model

data class RutinaModel(
    var nombre: String,
    var sesiones: Int,
    var minutos_trabajo: Int,
    var segundos_trabajo: Int,
    var minutos_descanso: Int,
    var segundos_descanso: Int
) {
}