package com.example.fitnesswatchapp.ui.scheduleSetting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesswatchapp.domain.InsertRutinaUseCase
import com.example.fitnesswatchapp.domain.model.Rutina
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleSettingViewModel @Inject constructor(
    private val insertRutinaUseCase: InsertRutinaUseCase
) : ViewModel() {
    /**
     * el live data permite a nuestro activity o fragment suscribirnos
     * a un modelo de datos que se llama automaticamente cuando se realiza un cambio
     * en dicho modeo
     */

    var rutina = Rutina(0,"", 1, 0,0,0,0)
    var vacio = true

    private val _rutinaModel = MutableLiveData<Rutina>()
    val rutinaModel: LiveData<Rutina> get() = _rutinaModel


    // contador para el número de sesiones

    fun onMenosSesiones() {
        if (rutina.sesiones > 1) {
            rutina.sesiones--
            _rutinaModel.value = rutina
        } else {
            rutina.sesiones = 60
            _rutinaModel.value = rutina
        }
    }

    fun onMasSesiones() {
        if (rutina.sesiones < 60) {
            rutina.sesiones ++
            _rutinaModel.value = rutina
        } else {
            rutina.sesiones = 0
            _rutinaModel.value = rutina
        }
    }

    // contador para los minutos de trabajo

    fun onMenosTrabajoMinutos() {
        if (rutina.minutos_trabajo > 0) {
            rutina.minutos_trabajo--
            _rutinaModel.value = rutina
        } else {
            rutina.minutos_trabajo = 60
            _rutinaModel.value = rutina
        }
    }

    fun onMasTrabajoMinutos() {
        if (rutina.minutos_trabajo < 60) {
            rutina.minutos_trabajo++
            _rutinaModel.value = rutina
        } else {
            rutina.minutos_trabajo = 0
            _rutinaModel.value = rutina
        }
    }

    // contador para los segundos de trabajo

    fun onMenosTrabajoSegundos() {
        if (rutina.segundos_trabajo > 0) {
            rutina.segundos_trabajo--
            _rutinaModel.value = rutina
        } else {
            rutina.segundos_trabajo = 60
            _rutinaModel.value = rutina
        }
    }

    fun onMasTrabajoSegundos() {
        if (rutina.segundos_trabajo < 60) {
            rutina.segundos_trabajo++
            _rutinaModel.value = rutina
        } else {
            rutina.segundos_trabajo = 0
            _rutinaModel.value = rutina
        }
    }

    // contador para los minutos de descanso

    fun onMenosDescansoMinutos() {
        if (rutina.minutos_descanso > 0) {
            rutina.minutos_descanso--
            _rutinaModel.value = rutina
        } else {
            rutina.minutos_descanso = 60
            _rutinaModel.value = rutina
        }
    }

    fun onMasDescansoMinutos() {
        if (rutina.minutos_descanso < 60) {
            rutina.minutos_descanso++
            _rutinaModel.value = rutina
        } else {
            rutina.minutos_descanso = 0
            _rutinaModel.value = rutina
        }
    }

    // contador para los segundos de descanso

    fun onMenosDescansoSegundos() {
        if (rutina.segundos_descanso > 0) {
            rutina.segundos_descanso--
            _rutinaModel.value = rutina
        } else {
            rutina.segundos_descanso = 60
            _rutinaModel.value = rutina
        }
    }

    fun onMasDescansoSegundos() {
        if (rutina.segundos_descanso < 60) {
            rutina.segundos_descanso++
            _rutinaModel.value = rutina
        } else {
            rutina.segundos_descanso = 0
            _rutinaModel.value = rutina
        }
    }

    fun validar(): Boolean {
        vacio = when {
            rutina.equals(rutinaVacia()) -> true
            rutina.minutos_trabajo == 0 && rutina.segundos_trabajo == 0 -> true
            rutina.minutos_descanso == 0 && rutina.segundos_descanso == 0 -> true
            else -> false
        }
        return vacio
    }

    private fun rutinaVacia(): Rutina {
        return Rutina(0,"", 1, 0,0,0,0)
    }

    /**
     * función para guardar la rutina en la base de datos
     */
    fun guardarRutina(nombre: String) {
        rutina.nombre = nombre
        viewModelScope.launch { insertRutinaUseCase(rutina) }
    }

    /**
     * función para validar el nombre de la rutina a guardar
     */
    fun nombreValido(nombre: String): Boolean {
        return nombre.isNotEmpty()
    }
}