package com.example.fitnesswatchapp.ui.tasksList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesswatchapp.domain.DeleteRutinaUseCase
import com.example.fitnesswatchapp.domain.GetRutinasUseCase
import com.example.fitnesswatchapp.domain.model.Rutina
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val getRutinasUseCase: GetRutinasUseCase,
    private val deleteRutinaUseCase: DeleteRutinaUseCase,
) : ViewModel() {

    private val _rutinas = MutableLiveData<List<Rutina>>()
    val rutinas: LiveData<List<Rutina>> get() = _rutinas

    /**
     * función que se ejecutará al iniciar su activity correspondiente
     */
    fun getRutinas() {
        viewModelScope.launch(Dispatchers.Main) {
            // traer a todas las rutinas de la BD para mostrarlas con recicler view
            val response = withContext(Dispatchers.IO) { getRutinasUseCase() }
            _rutinas.value = response
        }
    }

    fun deleteRutina(rutina: Rutina) {
        viewModelScope.launch {
            deleteRutinaUseCase(rutina)
        }
        getRutinas()
    }
}