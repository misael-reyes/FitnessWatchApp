package com.example.fitnesswatchapp.domain

import com.example.fitnesswatchapp.data.RutinaRepository
import javax.inject.Inject

class DeleteAllRutinasUseCase @Inject constructor(
    private val repository: RutinaRepository
) {

    suspend operator fun invoke() = repository.deleteRutinas()
}