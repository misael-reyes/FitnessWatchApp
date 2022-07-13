package com.example.fitnesswatchapp.domain

import com.example.fitnesswatchapp.data.RutinaRepository
import com.example.fitnesswatchapp.domain.model.Rutina
import javax.inject.Inject

class GetRutinasUseCase @Inject constructor(
    private val repository: RutinaRepository
) {

    /**
     * suspend operator fun invoke se hace para el siguiente caso:
     * en otra clase solo es necesario colocar GetRutinasUseCase() para invocar a
     * dicha función, sin tener que crear una instancia
     */

    /**
    suspend operator fun invoke():List<Rutina> {
        return repository.getAllRutinasFromDatabase()
    }**/
    // esto es lo mismo
    suspend operator fun invoke():List<Rutina> = repository.getAllRutinasFromDatabase()
}