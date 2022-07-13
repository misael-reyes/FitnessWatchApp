package com.example.fitnesswatchapp.domain

import com.example.fitnesswatchapp.data.RutinaRepository
import com.example.fitnesswatchapp.data.database.entities.toDatabase
import com.example.fitnesswatchapp.domain.model.Rutina
import javax.inject.Inject

class DeleteRutinaUseCase @Inject constructor(
    private val repository: RutinaRepository
) {
    suspend operator fun invoke(rutina: Rutina) = repository.deleteRutina(rutina.toDatabase())
}