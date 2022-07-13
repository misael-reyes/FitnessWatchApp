package com.example.fitnesswatchapp.data

import com.example.fitnesswatchapp.data.database.dao.RutinaDao
import com.example.fitnesswatchapp.data.database.entities.RutinaEntity
import com.example.fitnesswatchapp.domain.model.Rutina
import com.example.fitnesswatchapp.domain.model.toDomain
import javax.inject.Inject

/**
 * clase encargadda de gestionar si accederemos a los datos de la red o de la base local
 * en este caso solo tenemos datos en BD local, pero es buena practica colocar un repositorio
 * por si en un futuro se agregan más fuentes de datos al proyecto
 */
class RutinaRepository @Inject constructor(
    private val rutinaDao: RutinaDao
) {

    suspend fun getAllRutinasFromDatabase(): List<Rutina> {
        val response: List<RutinaEntity> = rutinaDao.getAllRutinas()
        // Rutina es el model para el domain y la UI
        return response.map {
            it.toDomain()
        }
    }

    suspend fun insertRutina(rutina: RutinaEntity) {
        rutinaDao.insertRutina(rutina)
    }

    suspend fun deleteRutinas() {
        rutinaDao.deleteAllRutinas()
    }

    suspend fun deleteRutina(rutina: RutinaEntity) {
        rutinaDao.deleteRutina(rutina)
    }
}