package com.example.fitnesswatchapp.ui.clock

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.fitnesswatchapp.domain.model.Rutina
import dagger.hilt.android.lifecycle.HiltViewModel

class ClockViewModel(rut: Rutina) : ViewModel() {

    // rutina a ejecutar
    var rutina = rut

    /**
     * el mutable live data privado nos permitira asignarle nuevos valores
     * dentro de esté view model
     * mientras que el live data normal solo nos permite leerlos y suscribirnos
     * pero no modificarlos y es el que se expone hacia afuera
     */

    private val _sesionActual = MutableLiveData(1)
    val sesionActual: LiveData<Int> get() = _sesionActual

    private val _minutos = MutableLiveData(rutina.minutos_trabajo)
    val minutos: LiveData<Int> get() = _minutos

    private val _segundos = MutableLiveData(rutina.segundos_trabajo)
    val segundos: LiveData<Int> get() = _segundos

    private val _rutinaTerminada = MutableLiveData(false)
    val rutinaTerminada: LiveData<Boolean> get() = _rutinaTerminada

    private val _trabajando = MutableLiveData(true)
    val trabajando: LiveData<Boolean> get() = _trabajando

    private val _cronometroPausado = MutableLiveData(false)
    val cronometroPausado: LiveData<Boolean> get() = _cronometroPausado


    private var workTime: Long = 0L // tiempo de trabajo
    private var restTime: Long = 0L // tiempo de descanso
    private var repeat = 1 // contador para el número de repeticiones en el timer
    private var sesion = 1 // contador para las sesiones
    private var pauseTime: Long = 0L // para recuperar el tiempo en caso de pausa

    init {

        // convertimos el tiempo que nos dio el usario a milisegundos para usar el timer

        workTime = convertToMillisTime(rutina.minutos_trabajo, rutina.segundos_trabajo)
        restTime = convertToMillisTime(rutina.minutos_descanso, rutina.segundos_descanso)

        // iniciamos el timer
        timer(workTime).start()
    }

    /**
     * cronometro
     */
    private fun timer(millisInFuture: Long, countDownInterval: Long = 1000): CountDownTimer {
        return object : CountDownTimer(millisInFuture, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                if (_cronometroPausado.value == true) {
                    // recuperamos el tiempo donde el usario dio clic en pausa

                    pauseTime = millisUntilFinished + 1000
                    cancel()
                } else {
                    // convertimos de milisegundos a minutos y a segundos para mostrarlos en la UI

                    val minutes: Int = ((millisUntilFinished + 1000) / 60000).toInt()
                    val seconds: Int = (((millisUntilFinished + 1000) % 60000) / 1000).toInt()
                    _minutos.value = minutes
                    _segundos.value = seconds
                }
            }

            override fun onFinish() {
                if (repeat < ((rutina.sesiones * 2) - 1))
                    repeatTimer()
                else
                    _rutinaTerminada.value = true
            }
        }
    }

    /**
     * función para repetir el cronometro, tomando en cuenta si se trata de tiempo
     * de trabajo o descanso
     */
    private fun repeatTimer() {
        repeat ++
        if (_trabajando.value == true) {
            _trabajando.value = false
            timer(restTime).start()
        } else {
            _trabajando.value = true
            sesion ++
            _sesionActual.value = sesion
            timer(workTime).start()
        }
    }

    /**
     * función para convertir a milisegundos el tiempo que nos dio el usario
     */
    private fun convertToMillisTime(min: Int, seg: Int): Long {
        var millis = (min * 60000).toLong()
        millis += (seg * 1000).toLong()
        return millis
    }

    /**
     * función para pausar el temporisador
     */
    fun pauseTimer() {
        _cronometroPausado.value = true
    }

    /**
     * función para reanudar el temporisador
     */
    fun resumeTimer() {
        _cronometroPausado.value = false
        timer(pauseTime).start()
    }
}

/**
 * necesitamos una clase viewmodel factory ya que nos pasarán de parametro la rutina con la que
 * trabajaremos
 * esta clase solo nos permite instanciar un objeto de la clase ClockViewModel
 */
class ClockViewModelFactory(private val rutina: Rutina) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClockViewModel(rutina) as T
    }
}