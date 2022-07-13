package com.example.fitnesswatchapp.domain

import com.example.fitnesswatchapp.data.RutinaRepository
import com.example.fitnesswatchapp.data.database.entities.toDatabase
import com.example.fitnesswatchapp.domain.model.Rutina
import javax.inject.Inject

class InsertRutinaUseCase @Inject constructor(
    private val repository: RutinaRepository
) {

    // con toDabase convertimos un obj de Rutina a RutinaEntity
    suspend operator fun invoke(rutina: Rutina) = repository.insertRutina(rutina.toDatabase())
}